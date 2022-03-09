/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.authentication.domain.interactors.RegisterUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kotlinx.coroutines.flow.flowOf

class FakeRegisterUseCase {
    val mock: RegisterUseCase = mockk()

    fun mockAndReturn(
        result: AuthResult,
        credentials: Credentials = Credentials(),
    ) {
        coEvery {
            mock.execute(credentials)
        } returns flowOf(result)
    }
}
