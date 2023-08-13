package com.example.deloittetask.data.local

import android.content.SharedPreferences
import com.example.deloittetask.data.AppPreferenceDataSource
import javax.inject.Inject

class AppPreferenceDataSourceImpl @Inject constructor(private val prefs: SharedPreferences) :
    AppPreferenceDataSource {
    override var nationalId: Long?
        get() = prefs.getLong(KEY_PREF_NATIONAL_ID, 0L)
        set(value) {
            putLong(KEY_PREF_NATIONAL_ID, value ?: 0L)
        }

    override var isLoggedIn: Boolean?
        get() = prefs.getBoolean(KEY_PREF_IS_USER_LOGGED_IN, false)
        set(value) {
            putBoolean(KEY_PREF_IS_USER_LOGGED_IN, value ?: false)
        }

    private fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    private fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    companion object {
        const val KEY_PREF_NATIONAL_ID = "keyPrefNationalId"
        const val KEY_PREF_IS_USER_LOGGED_IN = "keyPrefIsUserLoggedIn"
    }
}