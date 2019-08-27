package com.rygital.core.di

import android.content.Context
import com.rygital.core.system.ResourceManager

interface CoreAndroidApi {
    @ApplicationContext
    fun context(): Context

    fun resourceManager(): ResourceManager
}