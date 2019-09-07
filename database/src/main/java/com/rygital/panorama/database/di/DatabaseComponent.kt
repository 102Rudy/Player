package com.rygital.panorama.database.di

import android.content.Context
import androidx.room.Room
import com.rygital.core.data.AudioFileRepository
import com.rygital.core.di.ApplicationContext
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.di.DatabaseApi
import com.rygital.panorama.database.AppDatabase
import com.rygital.panorama.database.dao.AudioFileDao
import com.rygital.panorama.database.data.AudioFileRepositoryImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class DatabaseScope

@DatabaseScope
@Component(
        modules = [
            DatabaseBindsModule::class,
            DatabaseModule::class
        ],
        dependencies = [
            CoreAndroidApi::class
        ]
)
interface DatabaseComponent : DatabaseApi


@Module
internal abstract class DatabaseBindsModule {

    @Binds
    abstract fun provideAudioFileRepository(impl: AudioFileRepositoryImpl): AudioFileRepository
}


@Module
internal class DatabaseModule {

    companion object {
        private const val DATABASE_FILE_NAME: String = "player_database"
    }

    @Provides
    @DatabaseScope
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_FILE_NAME)
                    .build()

    @Provides
    @DatabaseScope
    fun provideAudioFileDao(database: AppDatabase): AudioFileDao = database.audioFileDao()
}