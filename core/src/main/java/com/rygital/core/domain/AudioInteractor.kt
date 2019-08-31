package com.rygital.core.domain

import com.rygital.core.model.AudioFile
import com.rygital.core.model.PlayerState
import kotlinx.coroutines.channels.ReceiveChannel

interface AudioInteractor {

    val audioFileChannel: ReceiveChannel<AudioFile>
    val playerStateChannel: ReceiveChannel<PlayerState>

    fun initialize()
    fun onBackground()
    fun onForeground()

    suspend fun open(audioFile: AudioFile)

    suspend fun play()
    suspend fun pause()
    fun stop()
}