package com.rygital.core.domain

import com.rygital.core.model.AudioFile

interface AudioInteractor {
    fun initialize()
    fun onBackground()
    fun onForeground()

    fun open(audioFile: AudioFile)

    fun play()
    fun pause()
    fun stop()
}