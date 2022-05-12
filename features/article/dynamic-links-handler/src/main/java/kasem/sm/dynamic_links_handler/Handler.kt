/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.dynamic_links_handler

import android.content.Intent
import android.net.Uri
import androidx.core.app.ComponentActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

/**
 * [Reference](https://medium.com/firebase-developers/beginners-guide-on-creating-a-shareable-link-of-your-apps-specific-data-with-firebase-8c471e1fe22e)
 */
fun ComponentActivity.handleDynamicLink(
    navigateToDetailScreen: (Int) -> Unit,
    onError: (Throwable) -> Unit,
) {
    try {
        // Handle when app is opened via shareable link
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                deepLink?.let { link ->
                    val path =
                        link.toString().substring(link.toString().lastIndexOf("/") + 1)
                    when {
                        /**
                         * Only execute if the sharing link contains 'article' in it as our
                         * shareable link would look like https://slimektopensource.page.link/article/{articleId}
                         */
                        link.toString().contains("article") -> {
                            val articleId = try {
                                path.toInt()
                            } catch (e: Exception) {
                                null
                            }

                            articleId?.let {
                                navigateToDetailScreen(it)
                                // Clear the id from Intent
                                intent.apply {
                                    data = null
                                    Intent()
                                }
                            }
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                onError(exception)
            }
    } catch (e: Exception) {
        onError(e)
    }
}
