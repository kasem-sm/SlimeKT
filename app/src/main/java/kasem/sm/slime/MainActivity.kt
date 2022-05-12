/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import coil.ImageLoader
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kasem.sm.common_ui.util.Destination
import kasem.sm.slime.ui.navigation.SlimeNavigation
import timber.log.Timber

@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @RequiresApi(S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberAnimatedNavController(bottomSheetNavigator)

            SlimeNavigation(
                navController = navController,
                imageLoader = imageLoader,
                bottomSheetNavigator = bottomSheetNavigator
            )

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

                                articleId?.let { id ->
                                    navController.navigate(Destination.articleDetail(id))
                                    // Clear the id from Intent
                                    intent.apply {
                                        data = null
                                        Intent()
                                    }
                                }
                            }
                        }
                    }
                }.addOnFailureListener {
                    Timber.d(it.message)
                }
        }
    }
}
