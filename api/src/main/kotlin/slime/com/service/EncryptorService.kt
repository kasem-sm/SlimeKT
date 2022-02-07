package slime.com.service

import io.ktor.util.hex
import slime.com.utils.ALGORITHM
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class EncryptorService(
    secretKey: String
) {

    private val hMacKey = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)

    fun encryptPassword(password: String) = Mac.getInstance(ALGORITHM).run {
        init(hMacKey)
        hex(doFinal(password.toByteArray(Charsets.UTF_8)))
    }
}
