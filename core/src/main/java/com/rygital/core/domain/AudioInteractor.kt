package com.rygital.core.domain

import com.rygital.core.model.AudioFile
import kotlinx.coroutines.channels.ReceiveChannel

interface AudioInteractor {
    val currentAudioFileChannel: ReceiveChannel<AudioFile>

    fun initialize()
    fun onBackground()
    fun onForeground()

    suspend fun open(audioFile: AudioFile)

    fun play()
    fun pause()
    fun stop()
}