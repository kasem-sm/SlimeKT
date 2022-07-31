/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.litote.kmongo.eq
import org.litote.kmongo.ne
import slime.com.data.models.AuthorResponse
import slime.com.data.models.User
import slime.com.data.repository.auth.AuthRepository
import slime.com.utils.ServiceResult
import slime.com.utils.containsOnlyNumbers
import slime.com.utils.containsSpecialCharacters
import java.util.Date

class UserService(
    private val authRepository: AuthRepository,
    private val jwtDomain: String,
    private val jwtAudience: String,
    private val jwtSecret: String
) {

    init {
        println("JwtAud $jwtAudience")
    }

    private val encryptorService = EncryptorService(jwtSecret)

    suspend fun String.getUser() = authRepository.findUserByUsername(this)

    suspend fun String.getUserById() = authRepository.findById(this)

    suspend fun getRandomUser(excludedUserId: String): AuthorResponse? {
        return authRepository.getAllUsers().find().filter(User::id ne excludedUserId)
            .filter(User::isUserDiscoverable eq true).toList().randomOrNull()?.let {
                AuthorResponse(it.username, it.id)
            }
    }

    suspend fun validateCredentialsForRegistration(
        username: String,
        password: String,
        isUserDiscoverable: Boolean
    ): ServiceResult {
        return when {
            (username.isBlank() || password.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
            (username.trim().length !in (4..10)) -> ServiceResult.Error("Username length should be between 4 to 10 characters")
            (password.trim().length !in (4..20)) -> ServiceResult.Error("Password length should be between 4 to 20 characters")
            username.containsOnlyNumbers() -> ServiceResult.Error("Username should not only consists of numbers")
            username.containsSpecialCharacters() -> ServiceResult.Error("Special characters are not allowed inside username")
            !authRepository.isUsernameAvailable(username) -> ServiceResult.Error("The username is not available")
            else -> registerNewUser(username, password, isUserDiscoverable)
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

    private suspend fun registerNewUser(
        username: String,
        password: String,
        isUserDiscoverable: Boolean
    ): ServiceResult {
        authRepository.createUser(
            User(
                username = username,
                password = encryptorService.encryptPassword(password),
                isUserDiscoverable = isUserDiscoverable
            )
        ).run {
            return ServiceResult.Success("Account created successfully")
        }
    }

    private suspend fun loginUserAndGenerateToken(userName: String): ServiceResult {
        authRepository.findUserByUsername(userName)?.run {
            val expiresIn = 4102504735000L
            val token = JWT.create().withClaim("userId", id).withIssuer(jwtDomain)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn)).withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            return ServiceResult.Success(token)
        }
        return ServiceResult.Error("Failed to log in, please try again later")
    }
}
