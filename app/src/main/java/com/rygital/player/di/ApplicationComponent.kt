package com.rygital.player.di

import android.content.Context
import android.content.SharedPreferences
import com.rygital.core.di.ApplicationContext
import com.rygital.core.di.ApplicationScope
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.system.PreferencesWrapper
import com.rygital.core.system.PreferencesWrapperImpl
import com.rygital.core.system.ResourceManager
import com.rygital.player.system.ResourceManagerImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides


@ApplicationScope
@Component(
        modules = [
            ApplicationModule::class,
            ApplicationProvidesModule::class
        ]
)
interface ApplicationComponent : CoreAndroidApi {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(@ApplicationContext context: Context): Builder

        fun build(): ApplicationComponent
    }
}

@Module
abstract class ApplicationModule {
    @Binds
    abstract fun provideResourceManager(impl: ResourceManagerImpl): ResourceManager

    @Binds
    abstract fun providePreferencesWrapper(impl: PreferencesWrapperImpl): PreferencesWrapper
}

@Module
class ApplicationProvidesModule {

    companion object {
        private const val PREFERENCES_FILENAME: String = "player_prefs"
    }

    @Provides
    @ApplicationScope
    fun provideSharedPrerences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
}