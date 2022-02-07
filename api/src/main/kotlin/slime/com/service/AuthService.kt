package slime.com.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import slime.com.data.models.User
import slime.com.data.repository.auth.AuthRepository
import slime.com.utils.ServiceResult
import slime.com.utils.containsOnlyNumbers
import slime.com.utils.containsSpecialCharacters
import java.util.Date

class AuthService(
    private val authRepository: AuthRepository,
    private val jwtDomain: String,
    private val jwtAudience: String,
    private val jwtSecret: String
) {

    private val encryptorService = EncryptorService(jwtSecret)

    suspend fun String.getUser() = authRepository.findUserByUsername(this)

    suspend fun String.getUserById() = authRepository.findById(this)

    suspend fun validateCredentialsForRegistration(user: User): ServiceResult {
        user.apply {
            return when {
                (username.isBlank() || password.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
                (username.trim().length !in (4..10)) -> ServiceResult.Error("Username length should be between 4 to 10 characters")
                (password.trim().length !in (4..20)) -> ServiceResult.Error("Password length should be between 4 to 20 characters")
                username.containsOnlyNumbers() -> ServiceResult.Error("Username should not only consists of numbers")
                username.containsSpecialCharacters() -> ServiceResult.Error("Special characters are not allowed inside username")
                !authRepository.isUsernameAvailable(username) -> ServiceResult.Error("The username is not available")
                else -> registerNewUser(user.username, user.password)
            }
        }
    }

    suspend fun validateCredentialsForLogin(userName: String, password: String): ServiceResult {
        return when {
            (userName.isBlank() || password.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
            authRepository.isUsernameAvailable(userName) -> ServiceResult.Error("No such user exists.")
            !authRepository.verifyPasswordForUsername(
                userName,
                encryptorService.encryptPassword(password)
            ) -> ServiceResult.Error("Invalid Credentials")
            else -> loginUserAndGenerateToken(userName)
        }
    }

    private suspend fun registerNewUser(username: String, password: String): ServiceResult {
        authRepository.createUser(User(username, encryptorService.encryptPassword(password))).run {
            return ServiceResult.Success("Account created successfully")
        }
    }

    private suspend fun loginUserAndGenerateToken(userName: String): ServiceResult {
        authRepository.findUserByUsername(userName)?.run {
            val expiresIn = 4102504735000L
            val token = JWT.create()
                .withClaim("userId", id)
                .withIssuer(jwtDomain)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            return ServiceResult.Success(token)
        }
        return ServiceResult.Error("Failed to log in, please try again later")
    }
}
