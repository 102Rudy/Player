package com.rygital.core.system

import android.content.SharedPreferences
import com.rygital.core.di.ApplicationScope
import javax.inject.Inject

interface PreferencesWrapper {
    fun getThemeMode(): ThemeMode
    fun setThemeMode(themeMode: ThemeMode)
}

@ApplicationScope
class PreferencesWrapperImpl @Inject constructor(
        private val preferences: SharedPreferences
) : PreferencesWrapper {

    companion object {
        private const val KEY_THEME_MODE: String = "key_theme_mode"
    }

    override fun getThemeMode(): ThemeMode =
            ThemeMode.values()[preferences.getInt(KEY_THEME_MODE, ThemeMode.DARK.ordinal)]

    override fun setThemeMode(themeMode: ThemeMode) {
        preferences.edit().putInt(KEY_THEME_MODE, themeMode.ordinal).apply()
    }
}