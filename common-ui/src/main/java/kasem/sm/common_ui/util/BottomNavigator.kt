/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import kotlinx.coroutines.flow.Flow

interface BottomNavigator {
    fun navigate(item: BottomNavigationItems)
    fun isBottomNavItemSelectedAsFlow(item: BottomNavigationItems): Flow<Boolean>
}
