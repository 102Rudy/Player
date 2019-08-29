package com.rygital.player.explorer.di

import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
import com.rygital.player.explorer.data.ExplorerRepositoryImpl
import com.rygital.player.explorer.data.FileManager
import com.rygital.player.explorer.data.FileManagerImpl
import com.rygital.player.explorer.domain.ExplorerInteractor
import com.rygital.player.explorer.domain.ExplorerInteractorImpl
import com.rygital.player.explorer.domain.ExplorerRepository
import com.rygital.player.explorer.presentation.ExplorerFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ExplorerScope

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