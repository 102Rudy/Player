package com.rygital.player

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rygital.audioplayer.di.AudioPlayerComponent
import com.rygital.audioplayer.di.DaggerAudioPlayerComponent
import com.rygital.core.di.ApplicationComponentProvider
import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.system.ThemeMode
import com.rygital.player.di.ApplicationComponent
import com.rygital.player.di.DaggerApplicationComponent
import timber.log.Timber


class App : Application(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent
    private lateinit var audioPlayerComponent: AudioPlayerComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .bindApplicationContext(this)
                .build()

        audioPlayerComponent = DaggerAudioPlayerComponent.builder()
                .coreAndroidApi(applicationComponent)
                .build()

        audioPlayerComponent.audioInteractor().initialize()

        updateTheme()
    }

    override fun getApplicationComponent(): CoreAndroidApi = applicationComponent

    override fun getAudioPlayerComponent(): AudioPlayerApi = audioPlayerComponent

    private fun updateTheme() {
        val preferencesWrapper = applicationComponent.preferencesWrapper()

        val mode = when (preferencesWrapper.getThemeMode()) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}