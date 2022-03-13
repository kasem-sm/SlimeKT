/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.tasks

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject
import kasem.sm.topic.datasource.cache.TopicDatabaseService

@HiltWorker
internal class ClearSessionTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val topicDbService: TopicDatabaseService,
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        topicDbService.removeAllTopicsFromSubscription()
        return Result.success()
    }

    companion object {
        const val TAG = "clear-session-task"
    }
}

class ClearSessionManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    fun execute() {
        val request = buildRequest()
        request.enqueueWorker()
    }

    private fun OneTimeWorkRequest.enqueueWorker() {
        workManager.enqueueUniqueWork(
            ClearSessionTask.TAG,
            ExistingWorkPolicy.KEEP,
            this
        )
    }

    private fun buildRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ClearSessionTask>()
            .addTag(ClearSessionTask.TAG)
            .setConstraints(constraints)
            .build()
    }
}
