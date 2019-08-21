package com.rygital.audioplayer.domain

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.di.AudioPlayerScope
import com.rygital.audioplayer.system.DeviceManager
import java.io.File
import javax.inject.Inject

interface AudioRepository {
    fun getCurrentPlaylist(): List<Song>
}

interface AudioInteractor {
    fun play(path: String)
}

@AudioPlayerScope
class AudioInteractorImpl @Inject constructor(
//    private val audioRepository: AudioRepository,
//        deviceManager: DeviceManager
) : AudioInteractor {

    init {
//        val (sampleRate, bufferSize) = deviceManager.getDeviceAudioInfo()
//        audioLibWrapper.initialize(sampleRate, bufferSize)
    }


    override fun play(path: String) {
//        audioLibWrapper.play(path, 0, File(path).length().toInt())
    }
}