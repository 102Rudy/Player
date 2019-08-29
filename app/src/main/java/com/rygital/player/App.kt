package com.rygital.player

import android.app.Application
import com.rygital.audioplayer.di.AudioPlayerComponent
import com.rygital.audioplayer.di.DaggerAudioPlayerComponent
import com.rygital.core.di.ApplicationComponentProvider
import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
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
    }

    override fun getApplicationComponent(): CoreAndroidApi = applicationComponent

    override fun getAudioPlayerComponent(): AudioPlayerApi = audioPlayerComponent
}