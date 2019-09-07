package com.rygital.player

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rygital.audioplayer.di.DaggerAudioPlayerComponent
import com.rygital.core.di.ApplicationComponentProvider
import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.di.DatabaseApi
import com.rygital.core.system.ThemeMode
import com.rygital.panorama.database.di.DaggerDatabaseComponent
import com.rygital.player.di.DaggerApplicationComponent
import timber.log.Timber


class App : Application(), ApplicationComponentProvider {

    private lateinit var _coreAndroidApi: CoreAndroidApi
    private lateinit var _databaseApi: DatabaseApi
    private lateinit var _audioPlayerApi: AudioPlayerApi

    override val coreAndroidApi: CoreAndroidApi get() = _coreAndroidApi
    override val databaseApi: DatabaseApi get() = _databaseApi
    override val audioPlayerApi: AudioPlayerApi get() = _audioPlayerApi

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        _coreAndroidApi = DaggerApplicationComponent.builder()
                .bindApplicationContext(this)
                .build()

        _databaseApi = DaggerDatabaseComponent.builder()
                .coreAndroidApi(coreAndroidApi)
                .build()

        _audioPlayerApi = DaggerAudioPlayerComponent.builder()
                .coreAndroidApi(coreAndroidApi)
                .build()

        audioPlayerApi.audioInteractor().initialize()

        updateTheme()
    }

    private fun updateTheme() {
        val preferencesWrapper = coreAndroidApi.preferencesWrapper()

        val mode = when (preferencesWrapper.getThemeMode()) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}