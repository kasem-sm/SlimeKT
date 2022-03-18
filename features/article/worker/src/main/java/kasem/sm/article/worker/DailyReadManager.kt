/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyReadManager @Inject constructor(
    private val workManager: WorkManager
) {
    fun execute() = enqueueWorker()

    private fun enqueueWorker() {
        workManager.enqueueUniquePeriodicWork(
            DailyReadTask.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            buildRequest()
        )
    }

    private fun buildRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<DailyReadTask>(24, TimeUnit.HOURS)
            .addTag(DailyReadTask.TAG)
            .setConstraints(getDRMConstraints())
            .build()
    }

    companion object {
        fun getDRMConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        }
    }
}
