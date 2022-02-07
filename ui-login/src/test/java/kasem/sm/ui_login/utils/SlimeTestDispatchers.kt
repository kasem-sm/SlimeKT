/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.utils

import kasem.sm.core.domain.SlimeDispatchers
import kotlinx.coroutines.test.StandardTestDispatcher

fun testDispatchers(): SlimeDispatchers {
    return StandardTestDispatcher().run {
        SlimeDispatchers(this, this, this)
    }
}
