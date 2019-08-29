package com.rygital.core.di

import com.rygital.core.domain.AudioInteractor

interface AudioPlayerApi {
    fun audioInteractor(): AudioInteractor
}