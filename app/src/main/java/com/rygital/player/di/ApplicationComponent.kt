package com.rygital.player.di

import android.content.Context
import com.rygital.audioplayer.di.AudioPlayerApi
import com.rygital.core.di.ApplicationContext
import com.rygital.core.di.ApplicationScope
import com.rygital.core.di.CoreAndroidApi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides


@ApplicationScope
@Component
interface ApplicationComponent : CoreAndroidApi {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(@ApplicationContext context: Context): Builder
        fun build(): ApplicationComponent
    }
}