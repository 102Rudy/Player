package com.rygital.core.di

interface ApplicationComponentProvider {
    fun getApplicationComponent(): CoreAndroidApi
    fun getAudioPlayerComponent(): AudioPlayerApi
}