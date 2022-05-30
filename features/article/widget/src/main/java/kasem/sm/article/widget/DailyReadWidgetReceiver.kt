/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll

class DailyReadWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DailyReadWidget()

    companion object {
        suspend fun updateWidget(articleTitle: String, context: Context) {
            val glanceId = GlanceAppWidgetManager(context).getGlanceIds(DailyReadWidget::class.java).last()
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[DailyReadWidget.articleTitlePreference] = articleTitle
            }
            DailyReadWidget().updateAll(context)
        }
    }
}
