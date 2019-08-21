package com.rygital.player

import android.app.Application
import com.rygital.core.di.ApplicationComponentProvider
import com.rygital.core.di.CoreAndroidApi
import com.rygital.player.di.ApplicationComponent
import com.rygital.player.di.DaggerApplicationComponent
import timber.log.Timber


class App : Application(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .bindApplicationContext(this)
                .build()
    }

    override fun getApplicationComponent(): CoreAndroidApi = applicationComponent
}