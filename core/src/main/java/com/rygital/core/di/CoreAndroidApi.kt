package com.rygital.core.di

import android.content.Context
import com.rygital.core.system.PreferencesWrapper
import com.rygital.core.system.ResourceManager

interface CoreAndroidApi {
    @ApplicationContext
    fun context(): Context

    fun resourceManager(): ResourceManager
    fun preferencesWrapper(): PreferencesWrapper
}