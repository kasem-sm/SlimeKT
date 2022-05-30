/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.widget

import android.content.Context
import androidx.compose.material3.MaterialTheme
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
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class DailyReadWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val state = currentState<Preferences>()
        val articleTitle = state[stringPreferencesKey("article_title")] ?: ""

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable(actionRunCallback<ActionUpdate>())
        ) {
            Text(
                text = articleTitle,
                modifier = GlanceModifier.padding(10.dp),
                style = TextStyle(
                    color = ColorProvider(MaterialTheme.colorScheme.onPrimaryContainer),
                    fontSize = 18.sp
                )
            )
        }
    }
}

class ActionUpdate : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[stringPreferencesKey("article_title")] = "Updated from Click"
        }
        DailyReadWidget().update(context, glanceId)
    }
}
