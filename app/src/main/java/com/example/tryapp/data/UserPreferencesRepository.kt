package com.example.tryapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.tryapp.UserPreferenceKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.internalDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")


@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.internalDataStore

    val userName: Flow<String> = dataStore.data.map { it[UserPreferenceKeys.USER_NAME] ?: "" }
    val userAge: Flow<Int> = dataStore.data.map { it[UserPreferenceKeys.USER_AGE] ?: 0 }
    val userAuthorized: Flow<Boolean> =
        dataStore.data.map { it[UserPreferenceKeys.USER_AUTHORIZED] ?: false }

    suspend fun saveUser(name: String, age: Int, authorized: Boolean) {
        dataStore.edit { prefs ->
            prefs[UserPreferenceKeys.USER_NAME] = name
            prefs[UserPreferenceKeys.USER_AGE] = age
            prefs[UserPreferenceKeys.USER_AUTHORIZED] = authorized
        }
    }
}
