/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.task_api

import kotlinx.coroutines.flow.Flow

interface Tasks {
    fun executeDailyReader()
    fun executeAuthenticationVerifier()
    fun updateSubscriptionStatus(ids: List<String>): Flow<Result<Unit>>
    fun clearUserSubscriptionLocally()

    fun execute() {
        executeDailyReader()
        executeAuthenticationVerifier()
    }
}
