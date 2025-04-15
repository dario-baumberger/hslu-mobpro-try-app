package com.example.tryapp

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object NotificationConstants {
    const val NOTIFICATION_ID = 23
    const val CHANNEL_ID = "ch.hslu.mobpro.demo.channel"
    const val CHANNEL_NAME = "Music notifications"
}

object UserPreferenceKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_AGE = intPreferencesKey("user_age")
    val USER_AUTHORIZED = booleanPreferencesKey("user_authorized")
}