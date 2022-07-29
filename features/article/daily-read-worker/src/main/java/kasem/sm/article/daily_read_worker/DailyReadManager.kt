/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.daily_read_worker

import androidx.work.*
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
        private fun getDRMConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        }
    }
}
