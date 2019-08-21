package com.rygital.audioplayer.di

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.domain.AudioInteractor
import com.rygital.audioplayer.domain.AudioInteractorImpl
import com.rygital.core.di.CoreAndroidApi
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AudioPlayerScope

interface AudioPlayerApi {
    fun audioInteractor(): AudioInteractor
}

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
abstract class AudioPlayerBindsModule {

    @Binds
    abstract fun provideAudioInteractor(impl: AudioInteractorImpl): AudioInteractor
}

@Module
class AudioPlayerModule {
    @Provides
    @AudioPlayerScope
    fun provideAudioLibWrapper() = AudioLibWrapper()
}