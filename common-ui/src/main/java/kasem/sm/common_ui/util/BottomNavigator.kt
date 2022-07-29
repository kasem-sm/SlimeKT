package kasem.sm.common_ui.util

import kotlinx.coroutines.flow.Flow

interface BottomNavigator {
    fun navigate(item: BottomNavigationItems)
    fun isBottomNavItemSelectedAsState(item: BottomNavigationItems): Flow<Boolean>
}