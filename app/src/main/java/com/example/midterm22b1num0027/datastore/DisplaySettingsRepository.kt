package com.example.midterm22b1num0027.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class DisplayMode(val mode: String) {
    BOTH("both"),
    FOREIGN("foreign"),
    MONGOLIAN("mongolian")
}

private val Context.dataStore by preferencesDataStore(name = "display_settings")

class DisplaySettingsRepository(private val context: Context) {
    companion object {
        val DISPLAY_MODE_KEY = stringPreferencesKey("display_mode")
    }
    val displayModeFlow: Flow<DisplayMode> = context.dataStore.data
        .map {preferences ->
            when (preferences[DISPLAY_MODE_KEY]) {
                DisplayMode.FOREIGN.mode -> DisplayMode.FOREIGN
                DisplayMode.MONGOLIAN.mode -> DisplayMode.MONGOLIAN
                else -> DisplayMode.BOTH
            }
        }
    suspend fun setDisplayMode(mode: DisplayMode) {
        context.dataStore.edit { preferences ->
            preferences[DISPLAY_MODE_KEY] = mode.mode
        }
    }
}
