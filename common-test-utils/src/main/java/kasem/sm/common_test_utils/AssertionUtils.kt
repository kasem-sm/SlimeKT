/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_test_utils

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import com.google.common.truth.Truth
import kasem.sm.core.domain.Stage
import kotlinx.coroutines.flow.Flow

infix fun Any?.shouldBe(assert: Any?) {
    Truth.assertThat(this).isEqualTo(assert)
}

infix fun Throwable?.shouldBe(assert: Throwable) {
    Truth.assertThat(this).isInstanceOf(assert::class.java)
}

infix fun Boolean.shouldBe(bool: Boolean) {
    if (bool) Truth.assertThat(this).isTrue()
    else Truth.assertThat(this).isFalse()
}

suspend inline fun Flow<Stage>.shouldBeInOrder(
    crossinline scope: suspend FlowTurbine<Stage>.() -> Unit
) {
    test {
        scope()
        cancelAndConsumeRemainingEvents()
    }
}
