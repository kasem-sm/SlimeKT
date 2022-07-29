/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.daily_read_worker.utils

import android.app.*
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kasem.sm.article.daily_read_worker.R
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showReminderNotification(
        articleId: Int,
        description: String,
        title: String,
        featuredImage: Bitmap? = null,
    ) {
        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(slimeNotificationChannel)
        }

        val builder = context.slimeNotificationCompatBuilder.apply {
            setContentTitle(title)
            setContentText(description)
            setSmallIcon(R.drawable.ic_home)
            setCategory(Notification.CATEGORY_REMINDER)
            setContentIntent(getPendingIntent(articleId))
            setLargeIcon(featuredImage)
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(featuredImage)
                    .bigLargeIcon(null)
            )
        }

        builder.priority = NotificationManager.IMPORTANCE_DEFAULT
        builder.setAutoCancel(true)
        manager.notify(1, builder.build())
    }

    private fun getPendingIntent(articleId: Int): PendingIntent {
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(articleId.openTaskIntent)
            getPendingIntent(
                1_11,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    companion object {
        private val Int.openTaskIntent
            get() = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://slime-kt.herokuapp.com/article_detail_screen=$this")
            )

        @RequiresApi(Build.VERSION_CODES.O)
        private val slimeNotificationChannel = NotificationChannel(
            "kasem.sm.slime.reminder",
            "Daily Read Reminder",
            IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = VISIBILITY_PUBLIC
            enableVibration(true)
            setBypassDnd(true)
        }

        private val Context.slimeNotificationCompatBuilder: NotificationCompat.Builder
            get() = NotificationCompat.Builder(this, "kasem.sm.slime.reminder")
    }
}
