package com.rygital.player.di

import com.rygital.audioplayer.di.AudioPlayerApi
import com.rygital.audioplayer.di.AudioPlayerComponent
import com.rygital.core.di.CoreAndroidApi
import com.rygital.player.data.ExplorerRepositoryImpl
import com.rygital.player.data.FileManager
import com.rygital.player.data.FileManagerImpl
import com.rygital.player.domain.ExplorerInteractor
import com.rygital.player.domain.ExplorerInteractorImpl
import com.rygital.player.domain.ExplorerRepository
import com.rygital.player.presentation.explorer.ExplorerFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ExplorerScope

@ExplorerScope
@Component(
        modules = [
            ExplorerModule::class
        ],
        dependencies = [
            CoreAndroidApi::class,
            AudioPlayerApi::class
        ]
)
interface ExplorerComponent {
    fun inject(fragment: ExplorerFragment)
}

@Module
internal abstract class ExplorerModule {
    @Binds
    abstract fun provideInteractor(impl: ExplorerInteractorImpl): ExplorerInteractor

    @Binds
    abstract fun provideRepository(impl: ExplorerRepositoryImpl): ExplorerRepository

    @Binds
    abstract fun provideFileManager(impl: FileManagerImpl): FileManager
}