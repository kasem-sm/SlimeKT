/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.subscription_manager_worker

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject

class ClearSubscriptionLocallyManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    fun execute() {
        val request = buildRequest()
        request.enqueueWorker()
    }

    private fun OneTimeWorkRequest.enqueueWorker() {
        workManager.enqueueUniqueWork(
            ClearSubscriptionLocally.TAG,
            ExistingWorkPolicy.KEEP,
            this
        )
    }

    private fun buildRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ClearSubscriptionLocally>()
            .addTag(ClearSubscriptionLocally.TAG)
            .setConstraints(constraints)
            .build()
    }
}
