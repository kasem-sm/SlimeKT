/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kasem.sm.topic.datasource.network.response.SlimeResponse
import kasem.sm.topic.worker.utils.SubscriptionState
import kasem.sm.topic.worker.utils.getSubscriptionState
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
internal class SubscribeTopicTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
) : CoroutineWorker(context, workParams) {

    @OptIn(ExperimentalTime::class)
    override suspend fun doWork(): Result {
        // starts from 0
        if (runAttemptCount >= MAXIMUM_RETRIES) return Result.failure()

        val ids = inputData.getStringArray(ID_KEY) ?: return Result.failure()

        val listOfResult = withContext(Dispatchers.Default) {
            ids.map { id ->
                async { api.subscribeIfNot(id) }
            }.awaitAll()
        }

        listOfResult
            .map { it.getOrThrow() }
            .forEach { it.updateCache() }

        return Result.success()
    }

    private suspend fun SlimeResponse<String>.updateCache(): Result {
        data?.let { id ->
            when (message?.getSubscriptionState()) {
                SubscriptionState.SUBSCRIBED -> cache.updateSubscriptionStatus(true, id)
                SubscriptionState.UNSUBSCRIBED -> cache.updateSubscriptionStatus(false, id)
                else -> return@let Result.retry()
            }
        } ?: return Result.retry()
        return Result.success()
    }

    companion object {
        private const val MAXIMUM_RETRIES = 3
        const val ID_KEY = "subscribe-topic-task-id"
        const val TAG = "subscribe-topic-task"

        fun buildData(ids: List<String>) = Data.Builder()
            .putStringArray(ID_KEY, ids.toTypedArray())
            .build()
    }
}
