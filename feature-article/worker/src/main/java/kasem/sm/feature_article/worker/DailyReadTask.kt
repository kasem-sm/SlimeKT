/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.feature_article.datasource.cache.ArticleDatabaseService
import kasem.sm.feature_article.datasource.network.ArticleApiService
import kasem.sm.feature_article.widget.DailyReadWidgetReceiver
import kasem.sm.feature_article.worker.utils.NotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@HiltWorker
internal class DailyReadTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val applicationScope: CoroutineScope,
    private val slimeDispatcher: SlimeDispatchers,
    private val notificationManager: NotificationManager,
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        return getAndCacheArticle()
    }

    private suspend fun getAndCacheArticle(): Result {
        // starts from 0
        if (runAttemptCount >= MAXIMUM_RETRIES) return Result.failure()

        val randomArticleFromApi = api.getRandomArticleFromSubscription().getOrElse {
            return Result.retry()
        }?.data?.let {
            val pair = cache.getRespectivePair(it.id)
            it.toEntity(pair)
        } ?: return Result.retry()

        Log.d("DailyReadTask", randomArticleFromApi.title)

        try {
            if (!randomArticleFromApi.isShownInDailyRead) {
                /**
                 * Remove previous article from daily read,
                 */
                // Update in cache
                applicationScope.launch(slimeDispatcher.defaultDispatcher) {
                    cache.run {
                        removePreviousActiveArticle()
                        insert(randomArticleFromApi)
                        updateDailyReadStatus(true, randomArticleFromApi.id)
                        updateIsActiveInDailyReadStatus(true, randomArticleFromApi.id)
                    }
                }.join()
                updateWidgetAndShowNotification(randomArticleFromApi.title)
                return Result.success()
            } else {
                return Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }

    private suspend fun updateWidgetAndShowNotification(articleTitle: String) {
        notificationManager.showReminderNotificationFor(
            articleId = 1,
            description = articleTitle,
            title = "Your daily read is ready",
        )

        DailyReadWidgetReceiver.updateWidget(articleTitle, context)
    }

    companion object {
        private const val MAXIMUM_RETRIES = 3
        const val TAG = "daily-read-manager"
    }
}
