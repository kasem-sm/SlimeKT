/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.widget

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class DailyReadWidget(
    private val articleTitle: String = DEFAULT,
) : GlanceAppWidget() {

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun Content() {
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

    companion object {
        const val DEFAULT = ""
    }
}

class ActionUpdate : ActionCallback {

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        DailyReadWidget("Updated from click").update(context, glanceId)
    }
}
