package slime.com.data.repository.auth

import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.User

class AuthRepositoryImpl(
    db: CoroutineDatabase
) : AuthRepository {

    private val userDb = db.getCollection<User>()

    override suspend fun createUser(user: User) {
        userDb.insertOne(user)
    }

    override suspend fun isUsernameAvailable(userName: String): Boolean {
        return userDb.findOne(User::username eq userName) == null
    }

    override suspend fun findUserByUsername(userName: String): User? {
        return userDb.findOne(User::username eq userName)
    }

    override suspend fun findById(id: String): User? {
        return userDb.findOne(User::id eq id)
    }

    override suspend fun verifyPasswordForUsername(userName: String, password: String): Boolean {
        val user = findUserByUsername(userName)
        return user?.password == password
    }

    override suspend fun verifyUsernameBelongsToUserId(userName: String, userId: String): Boolean {
        val user = findUserByUsername(userName)
        return user?.id == userId
    }

    override suspend fun getAllUsers(): CoroutineCollection<User> {
        return userDb
    }
}
