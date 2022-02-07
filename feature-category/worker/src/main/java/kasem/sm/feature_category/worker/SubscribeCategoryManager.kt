/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.worker

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import javax.inject.Inject
import kasem.sm.core.domain.Stage
import kasem.sm.feature_category.worker.utils.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class SubscribeCategoryManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    suspend fun updateSubscriptionStatus(
        ids: List<String>
    ): Flow<Stage> {
        return channelFlow {
            val request = buildRequest(ids)
            request.enqueueWorker()

            workManager.getWorkInfoByIdLiveData(request.id)
                .asFlow()
                .collectLatest { workInfo ->
                    if (workInfo != null) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> send(Stage.Success)
                            WorkInfo.State.FAILED -> send(Stage.Exception())
                            else -> Unit
                        }
                    }
                }
        }
    }

    private fun OneTimeWorkRequest.enqueueWorker() {
        workManager.enqueueUniqueWork(
            SubscribeCategoryTask.TAG,
            ExistingWorkPolicy.KEEP,
            this
        )
    }

    private fun buildRequest(ids: List<String>): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SubscribeCategoryTask>()
            .addTag(SubscribeCategoryTask.TAG)
            .setInputData(SubscribeCategoryTask.buildData(ids))
            .setConstraints(constraints)
            .build()
    }
}
