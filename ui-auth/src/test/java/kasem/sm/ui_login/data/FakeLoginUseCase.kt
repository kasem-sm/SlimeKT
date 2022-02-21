/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.data

import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.authentication.domain.interactors.LoginUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kotlinx.coroutines.flow.flowOf

class FakeLoginUseCase {

    val mock: LoginUseCase = mockk(relaxed = true)

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: AuthResult,
    ) {
        coEvery {
            mock.execute(credentials)
        } returns flowOf(result)
    }
}
