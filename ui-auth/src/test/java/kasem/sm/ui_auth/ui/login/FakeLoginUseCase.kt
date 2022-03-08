/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.ui.login

import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.authentication.domain.interactors.LoginUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kotlinx.coroutines.flow.flowOf

class FakeLoginUseCase {
    val mock: LoginUseCase = mockk()

    fun mockAndThrowErr(
        credentials: Credentials = Credentials(),
        someException: Exception = SecurityException()
    ) {
        coEvery {
            mock.execute(credentials)
        } throws someException
    }

    fun mockAndReturn(
        result: AuthResult,
        credentials: Credentials = Credentials(),
    ) {
        coEvery {
            mock.execute(credentials)
        } returns flowOf(result)
    }
}
