/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.session

import android.content.SharedPreferences
import javax.inject.Inject
import kasem.sm.core.interfaces.Session

class SessionImpl @Inject constructor(
    private val pref: SharedPreferences
) : Session {
    override suspend fun storeToken(token: String?) {
        pref.edit()
            .putString(AUTH_TOKEN_KEY, token)
            .apply()
    }

    override fun fetchToken(): String? {
        return pref.getString(AUTH_TOKEN_KEY, null)
    }

    companion object {
        const val AUTH_TOKEN_KEY = "kasem.sm.slime.user_auth_token"
        const val ON_BOARDING_KEY = "kasem.sm.slime.on_boarding_key"
    }
}
