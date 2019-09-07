package com.rygital.core.di

import com.rygital.core.data.AudioFileRepository

interface DatabaseApi {
    fun audioFileRepository(): AudioFileRepository
}