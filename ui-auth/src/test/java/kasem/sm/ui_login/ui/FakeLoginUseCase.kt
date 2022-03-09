/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
<<<<<<< HEAD:ui-auth/src/test/java/kasem/sm/ui_auth/login/FakeLoginUseCase.kt
package kasem.sm.ui_auth.login
=======
package kasem.sm.ui_login.ui
>>>>>>> dev:ui-auth/src/test/java/kasem/sm/ui_login/ui/FakeLoginUseCase.kt

import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.authentication.domain.interactors.LoginUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kotlinx.coroutines.flow.flowOf

class FakeLoginUseCase {
    val mock: LoginUseCase = mockk()

<<<<<<< HEAD:ui-auth/src/test/java/kasem/sm/ui_auth/login/FakeLoginUseCase.kt
=======
    fun mockAndThrowErr(
        credentials: Credentials = Credentials(),
        someException: Exception = SecurityException()
    ) {
        coEvery {
            mock.execute(credentials)
        } throws someException
    }

>>>>>>> dev:ui-auth/src/test/java/kasem/sm/ui_login/ui/FakeLoginUseCase.kt
    fun mockAndReturn(
        result: AuthResult,
        credentials: Credentials = Credentials(),
    ) {
        coEvery {
            mock.execute(credentials)
        } returns flowOf(result)
    }
}
