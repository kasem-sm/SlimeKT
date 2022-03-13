/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.authentication.datasource.network.AuthApiService
import kasem.sm.core.interfaces.AuthManager
import kasem.sm.core.session.AuthState

@HiltWorker
internal class CheckAuthenticationTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: AuthApiService,
    private val authManager: AuthManager,
) : CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= MAXIMUM_RETRIES) {
            authManager.clearSession()
            return Result.failure()
        }

        if (authManager.state.value == AuthState.LOGGED_IN) {
            val result = api.checkAuthenticationState().getOrElse {
                return Result.retry()
            }.data ?: return Result.retry()

            return when (result) {
                true -> Result.success()
                false -> {
                    authManager.clearSession()
                    Result.success()
                }
            }
        }
        return Result.success()
    }

    companion object {
        private const val MAXIMUM_RETRIES = 3
        const val TAG = "check-authentication-manager"
    }
}
