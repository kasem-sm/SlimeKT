/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.auth

import org.litote.kmongo.coroutine.CoroutineCollection
import slime.com.data.models.User

interface AuthRepository {
    suspend fun createUser(user: User)
    suspend fun isUsernameAvailable(userName: String): Boolean
    suspend fun findUserByUsername(userName: String): User?
    suspend fun findById(id: String): User?
    suspend fun verifyPasswordForUsername(userName: String, password: String): Boolean
    suspend fun verifyUsernameBelongsToUserId(userName: String, userId: String): Boolean
    suspend fun getAllUsers(): CoroutineCollection<User>
}
