/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.auth.principal
import slime.com.isDebugMode

fun Application.configureSecurity() {
    authentication {
        jwt {
            val jwtAudience = if (isDebugMode) "jwt_aud" else System.getenv("JWT_AUD")
            val jwtDomain = if (isDebugMode) "jwt_dom" else System.getenv("JWT_DO")
            val jwtSec = if (isDebugMode) "secret" else System.getenv("JWT_SEC")
            realm = if (isDebugMode) "jwt_realm" else System.getenv("JWT_RE")
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSec)).withAudience(jwtAudience)
                    .withIssuer(jwtDomain).build()
            )
            validate { credential ->
                val predicate = credential.payload.audience.contains(jwtAudience)
                JWTPrincipal(credential.payload) elseNull { predicate }
            }
        }
    }
}

val JWTPrincipal.userId: String?
    get() = getClaim("userId", String::class)

val ApplicationCall.userId: String?
    get() = principal<JWTPrincipal>()?.userId

infix fun <T> T.elseNull(predicate: () -> Boolean): T? {
    return if (predicate()) this else null
}
