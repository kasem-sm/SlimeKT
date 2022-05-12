/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.topic.datasource.cache.TopicDatabaseService

@HiltWorker
internal class ClearSubscriptionLocally @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val topicDbService: TopicDatabaseService,
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        topicDbService.updateSubscriptionStatus(false)
        return Result.success()
    }

    companion object {
        const val TAG = "clear-session-task"
    }
}
