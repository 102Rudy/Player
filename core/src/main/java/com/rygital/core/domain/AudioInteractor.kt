package com.rygital.core.domain

import com.rygital.core.model.AudioFile

interface AudioInteractor {
    fun initialize()
    fun play(audioFile: AudioFile)
    fun onBackground()
    fun onForeground()
}