/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.feature_category.datasource.cache.CategoryDatabaseService
import kasem.sm.feature_category.datasource.network.CategoryApiService

@HiltWorker
internal class SubscribeCategoryTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: CategoryApiService,
    private val cache: CategoryDatabaseService
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        // starts from 0
        if (runAttemptCount >= MAXIMUM_RETRIES) return Result.failure()

        val ids = inputData.getStringArray(ID_KEY) ?: return Result.failure()

        ids.forEach { id ->
            val isUserSubscribed = api.hasUserSubscribed(id).getOrElse {
                Log.d("SubscribeCategoryTask", it.message ?: "Something went wrong")
                return Result.retry()
            }

            when {
                isUserSubscribed.success && isUserSubscribed.data == true -> unsubscribe(id)
                isUserSubscribed.success && isUserSubscribed.data == false -> subscribe(id)
                else -> return Result.retry()
            }
        }
        return Result.success()
    }

    private suspend fun subscribe(id: String) {
        val apiResponse = api.subscribeToCategory(id).getOrNull() ?: return

        if (!apiResponse.success) {
            Log.d("SubscribeCategoryTask", apiResponse.message ?: "Something went wrong!")
        } else {
            updateCache(true, listOf(id))
        }
    }

    private suspend fun unsubscribe(id: String) {
        val apiResponse = api.unsubscribeToCategory(id).getOrNull() ?: return

        if (!apiResponse.success) {
            Log.d("SubscribeCategoryTask", apiResponse.message ?: "Something went wrong!")
        } else {
            updateCache(false, listOf(id))
        }
    }

    private suspend fun updateCache(status: Boolean, id: List<String>) {
        id.forEach {
            cache.updateSubscriptionStatus(status, it)
        }
    }

    companion object {
        private const val MAXIMUM_RETRIES = 3
        const val ID_KEY = "subscribe-category-task-id"
        const val TAG = "subscribe-category-task"

        fun buildData(ids: List<String>) = Data.Builder()
            .putStringArray(ID_KEY, ids.toTypedArray())
            .build()
    }
}
