/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile.components

import androidx.compose.runtime.Composable
import kasem.sm.common_ui.ProfileCard

@Composable
internal fun CurrentUserProfileView() {
    ProfileCard(
        username = "User 1234",
        bio = "Your Bio which won't exceed more than 2 lines! So you don't believe us? Try to read this or this",
    )
}
