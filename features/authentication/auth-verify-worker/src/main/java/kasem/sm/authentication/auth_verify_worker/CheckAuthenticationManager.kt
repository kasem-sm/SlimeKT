/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.auth_verify_worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CheckAuthenticationManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    fun execute() = enqueueWorker()

    private fun enqueueWorker() {
        workManager.enqueueUniquePeriodicWork(
            CheckAuthenticationTask.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            buildRequest()
        )
    }

    private fun buildRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<CheckAuthenticationTask>(24, TimeUnit.HOURS)
            .addTag(CheckAuthenticationTask.TAG)
            .setConstraints(constraints)
            .build()
    }
}
