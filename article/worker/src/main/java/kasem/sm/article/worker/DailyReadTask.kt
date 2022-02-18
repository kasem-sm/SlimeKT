/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.widget.DailyReadWidgetReceiver
import kasem.sm.article.worker.utils.NotificationManager
import kasem.sm.core.domain.SlimeDispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
internal class DailyReadTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
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

        Timber.d(randomArticleFromApi.title)

        return try {
            if (!randomArticleFromApi.isShownInDailyRead) {
                /**
                 * Remove previous article from daily read,
                 */
                withContext(slimeDispatcher.default) {
                    cache.run {
                        removePreviousActiveArticle()
                        insert(randomArticleFromApi)
                        updateDailyReadStatus(true, randomArticleFromApi.id)
                        updateIsActiveInDailyReadStatus(true, randomArticleFromApi.id)
                    }
                }
                updateWidgetAndShowNotification(
                    randomArticleFromApi.id,
                    randomArticleFromApi.title,
                )
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private suspend fun updateWidgetAndShowNotification(
        articleId: Int,
        articleTitle: String,
    ) {
        notificationManager.showReminderNotificationFor(
            articleId = articleId,
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
