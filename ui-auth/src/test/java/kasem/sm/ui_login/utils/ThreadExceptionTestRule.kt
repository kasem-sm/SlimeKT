/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.utils

import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A custom test watcher that sets the default uncaught
 * exception handler as that any exception inside of
 * a viewModelScope will actually fail a test.
 *
 * Inspiration - https://github.kasem/Kotlin/kotlinx.coroutines/issues/1205
 */

class ThreadExceptionTestRule : TestWatcher() {

    private var previousHandler: Thread.UncaughtExceptionHandler? = null

    override fun starting(description: Description?) {
        super.starting(description)
        previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            throw throwable
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Thread.setDefaultUncaughtExceptionHandler(previousHandler)
    }
}
