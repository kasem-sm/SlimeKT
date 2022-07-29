/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.subscription_manager_worker

import androidx.work.*
import kasem.sm.topic.subscription_manager_worker.utils.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SubscribeTopicManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    fun updateSubscriptionStatus(
        ids: List<String>
    ): Flow<Result<Unit>> {
        return channelFlow {
            val request = buildRequest(ids)
            request.enqueueWorker()

            workManager.getWorkInfoByIdLiveData(request.id)
                .asFlow()
                .collectLatest { workInfo ->
                    if (workInfo != null) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> send(Result.success(Unit))
                            WorkInfo.State.FAILED -> send(Result.failure(UnknownError()))
                            else -> Unit
                        }
                    }
                }
        }
    }

    private fun OneTimeWorkRequest.enqueueWorker() {
        workManager.enqueueUniqueWork(
            SubscribeTopicTask.TAG,
            ExistingWorkPolicy.KEEP,
            this
        )
    }

    private fun buildRequest(ids: List<String>): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SubscribeTopicTask>()
            .addTag(SubscribeTopicTask.TAG)
            .setInputData(SubscribeTopicTask.buildData(ids))
            .setConstraints(constraints)
            .build()
    }
}
