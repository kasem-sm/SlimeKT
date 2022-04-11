/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.service

import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import slime.com.utils.ALGORITHM

class EncryptorService(
    private val secretKey: String
) {
    fun encryptPassword(password: String) = Mac.getInstance(ALGORITHM).run {
        init(SecretKeySpec(secretKey.toByteArray(), ALGORITHM))
        hex(doFinal(password.toByteArray(Charsets.UTF_8)))
    }
}
