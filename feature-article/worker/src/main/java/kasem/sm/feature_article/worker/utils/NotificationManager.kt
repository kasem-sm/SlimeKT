/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.worker.utils

import android.app.Notification
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kasem.sm.feature_article.worker.R

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showReminderNotificationFor(
        articleId: Int,
        description: String,
        title: String
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
        val Int.openTaskIntent
            get() = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://slime.hk/article_detail_screen=$this")
            )

        @RequiresApi(Build.VERSION_CODES.O)
        val slimeNotificationChannel = NotificationChannel(
            "kasem.sm.slime.reminder",
            "Daily Read Reminder",
            IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = VISIBILITY_PUBLIC
            enableVibration(true)
            setBypassDnd(true)
        }

        val Context.slimeNotificationCompatBuilder: NotificationCompat.Builder
            get() = NotificationCompat.Builder(this, "kasem.sm.slime.reminder")
    }
}
