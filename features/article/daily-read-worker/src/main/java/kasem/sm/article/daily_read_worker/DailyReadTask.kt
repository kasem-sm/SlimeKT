/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.daily_read_worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kasem.sm.article.daily_read_worker.utils.NotificationManager
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.widget.DailyReadWidgetReceiver
import kasem.sm.core.domain.SlimeDispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
internal class DailyReadTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters,
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val dispatcher: SlimeDispatchers,
    private val notificationManager: NotificationManager,
    private val imageLoader: ImageLoader,
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
            val data = cache.getArticleData(it.id)
            it.toEntity(data)
        } ?: return Result.retry()

        Timber.d(randomArticleFromApi.title)

        return try {
            if (!randomArticleFromApi.isShownInDailyRead) {
                /**
                 * Remove previous article from daily read,
                 */
                withContext(dispatcher.default) {
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
                    randomArticleFromApi.featuredImage
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
        featuredImage: String,
    ) {
        context.getBitmap(
            imageLoader = imageLoader,
            imageUrl = featuredImage,
            onSuccess = { image ->
                notificationManager.showReminderNotification(
                    articleId = articleId,
                    description = articleTitle,
                    title = "Your daily read is ready",
                    featuredImage = image
                )

                DailyReadWidgetReceiver.updateWidget(articleTitle, context)
            }
        )
    }

    companion object {
        private const val MAXIMUM_RETRIES = 3
        const val TAG = "daily-read-manager"

        private suspend fun Context.getBitmap(
            imageLoader: ImageLoader,
            imageUrl: String,
            onSuccess: suspend (Bitmap) -> Unit,
        ) {
            val request = ImageRequest.Builder(this)
                .diskCachePolicy(CachePolicy.DISABLED)
                .data(imageUrl)
                .allowHardware(false)
                .build()

            val result = try {
                (imageLoader.execute(request) as SuccessResult).drawable
            } catch (e: Exception) {
                Timber.d("getBitmap Error ${e.message}")
                null
            }

            val bitmap = (result as BitmapDrawable).bitmap
            bitmap?.let {
                if (!it.isRecycled) {
                    onSuccess(bitmap)
                }
            }
        }
    }
}
