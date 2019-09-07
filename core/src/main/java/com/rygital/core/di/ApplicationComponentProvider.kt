package com.rygital.core.di

interface ApplicationComponentProvider {
    val coreAndroidApi: CoreAndroidApi
    val databaseApi: DatabaseApi
    val audioPlayerApi: AudioPlayerApi
}