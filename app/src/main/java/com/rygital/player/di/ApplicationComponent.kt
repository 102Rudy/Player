package com.rygital.player.di

import android.content.Context
import com.rygital.core.di.ApplicationContext
import com.rygital.core.di.ApplicationScope
import com.rygital.core.di.CoreAndroidApi
import com.rygital.core.system.ResourceManager
import com.rygital.player.system.ResourceManagerImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module


@ApplicationScope
@Component(
        modules = [
            ApplicationModule::class
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
}