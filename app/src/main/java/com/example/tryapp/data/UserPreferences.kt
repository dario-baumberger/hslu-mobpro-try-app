package com.example.tryapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.tryapp.UserPreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {


    val userName: Flow<String> =
        context.dataStore.data.map { it[UserPreferenceKeys.USER_NAME] ?: "" }
    val userAge: Flow<Int> = context.dataStore.data.map { it[UserPreferenceKeys.USER_AGE] ?: 0 }
    val userAuthorized: Flow<Boolean> = context.dataStore.data.map {
        it[
            UserPreferenceKeys.USER_AUTHORIZED
        ] ?: false
    }

    suspend fun saveUser(name: String, age: Int, authorized: Boolean) {
        context.dataStore.edit {
            it[UserPreferenceKeys.USER_NAME] = name
            it[UserPreferenceKeys.USER_AGE] = age
            it[UserPreferenceKeys.USER_AUTHORIZED] = authorized
        }
    }
}