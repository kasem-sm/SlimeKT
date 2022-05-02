/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileOutputStream
import kotlin.random.Random

fun SemanticsNodeInteraction.saveScreenshot(
    fileNamePrefix: String = Random.nextInt().toString(),
): SemanticsNodeInteraction {
    val bitmap = this
        .captureToImage()
        .asAndroidBitmap()

    saveScreenshot(
        fileName = fileNamePrefix + System.currentTimeMillis().toString(),
        bitmap = bitmap
    )
    return this
}

internal fun saveScreenshot(
    fileName: String,
    bitmap: Bitmap
) {
    val path = InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .filesDir
        .canonicalPath

    val fileN = "$path/$fileName.png"

    FileOutputStream(fileN).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    println("Screenshot saved to $fileN")
}
