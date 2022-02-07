/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyReadManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    fun executeDailyReader() = enqueueWorker()

    private fun enqueueWorker() {
        workManager.enqueueUniquePeriodicWork(
            DailyReadTask.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            buildRequest()
        )
    }

    private fun buildRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<DailyReadTask>(15, TimeUnit.MINUTES)
            .addTag(DailyReadTask.TAG)
            .setConstraints(constraints)
            .build()
    }
}
