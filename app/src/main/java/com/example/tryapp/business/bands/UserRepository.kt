/*package com.example.tryapp.business.bands

import android.content.Context
import com.example.tryapp.business.users.User



private object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_AGE = intPreferencesKey("user_age")
    val USER_AUTHORIZED = booleanPreferencesKey("user_authorized")
}

companion object {
    private const val USER_PREFERENCES_NAME = "user_preferences"
    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )
}

class UserRepository(private val context: Conetxt) {
    val user: Flow<User> = context.dataStore.data.mao { preference ->
        val userName = preference[PreferencesKeys.User]
    }
}*/