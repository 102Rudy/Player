package com.rygital.audioplayer.di

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.domain.AudioInteractorImpl
import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.domain.AudioInteractor
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class AudioPlayerScope

@AudioPlayerScope
@Component(
        modules = [
            AudioPlayerBindsModule::class,
            AudioPlayerModule::class
        ],
        dependencies = [
            CoreAndroidApi::class
        ]
)
interface AudioPlayerComponent : AudioPlayerApi

@Module
internal abstract class AudioPlayerBindsModule {

    @Binds
    abstract fun provideAudioInteractor(impl: AudioInteractorImpl): AudioInteractor
}

@Module
internal class AudioPlayerModule {
    @Provides
    @AudioPlayerScope
    fun provideAudioLibWrapper() = AudioLibWrapper()
}