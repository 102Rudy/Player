package com.rygital.player.widget.di

import com.rygital.core.di.AudioPlayerApi
import com.rygital.core.di.CoreAndroidApi
import com.rygital.player.widget.presentation.WidgetFragment
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class WidgetScope

@WidgetScope
@Component(
        dependencies = [
            CoreAndroidApi::class,
            AudioPlayerApi::class
        ]
)
interface WidgetComponent {
    fun inject(fragment: WidgetFragment)
}