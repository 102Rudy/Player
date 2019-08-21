package com.rygital.core.di

import android.content.Context

interface CoreAndroidApi {
    @ApplicationContext
    fun context(): Context
}