/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class DailyReadWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    override val sizeMode: SizeMode
        get() = SizeMode.Exact

    @Composable
    override fun Content() {
        val state = currentState<Preferences>()
        val articleTitle = state[articleTitlePreference] ?: ""

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .appWidgetBackground()
                .background(R.color.widget_background_color)
                .clickable(actionRunCallback<ActionUpdate>()),
        ) {
            Text(
                text = "Daily Read",
                modifier = GlanceModifier.padding(10.dp),
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_color),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = articleTitle,
                modifier = GlanceModifier.padding(horizontal = 10.dp),
                style = TextStyle(
                    color = ColorProvider(R.color.widget_text_color),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

    companion object {
        private const val ARTICLE_TITLE_KEY = "kasem.sm.article.widget.article_title_key"
        val articleTitlePreference =
            stringPreferencesKey(ARTICLE_TITLE_KEY)
    }
}

class ActionUpdate : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[DailyReadWidget.articleTitlePreference] = "Updated from Click"
        }
        DailyReadWidget().updateAll(context)
    }
}
