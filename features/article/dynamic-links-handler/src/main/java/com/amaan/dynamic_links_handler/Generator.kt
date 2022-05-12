/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.amaan.dynamic_links_handler

import android.net.Uri
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters

/**
 * [Reference](https://medium.com/firebase-developers/beginners-guide-on-creating-a-shareable-link-of-your-apps-specific-data-with-firebase-8c471e1fe22e)
 */
fun generateSharingLink(
    deepLink: Uri,
    previewImageLink: Uri,
    onFailure: (Exception) -> Unit,
    onSuccess: (String) -> Unit,
) {
    FirebaseDynamicLinks.getInstance().createDynamicLink().run {
        link = deepLink

        // [domainUriPrefix] will be the domain name you added when setting up Dynamic Links at Firebase Console.
        // You can find it in the Dynamic Links dashboard.
        domainUriPrefix = SLIME_DYNAMIC_LINK

        // Pass your preview Image Link here;
        setSocialMetaTagParameters(
            DynamicLink.SocialMetaTagParameters.Builder()
                .setImageUrl(previewImageLink)
                .build()
        )

        // Required
        androidParameters {
            build()
        }

        // Finally
        buildShortDynamicLink()
    }.also { task ->
        task.addOnSuccessListener { dynamicLink ->
            // This lambda will be triggered when short link generation is successful

            // Retrieve the newly created dynamic link so that we can use it further for sharing via Intent.
            onSuccess.invoke(dynamicLink.shortLink.toString())
        }
        task.addOnFailureListener { exception ->
            // This lambda will be triggered when short link generation failed due to an exception
            onFailure(exception)
        }
    }
}

const val SLIME_DYNAMIC_LINK = "https://slimektopensource.page.link"
