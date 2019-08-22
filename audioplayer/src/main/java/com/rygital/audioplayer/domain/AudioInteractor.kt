package com.rygital.audioplayer.domain

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.di.AudioPlayerScope
import com.rygital.audioplayer.system.DeviceManager
import com.rygital.core.model.AudioFile
import timber.log.Timber
import java.io.File
import javax.inject.Inject

interface AudioRepository {
    fun getCurrentPlaylist(): List<Song>
}

interface AudioInteractor {
    fun play(audioFile: AudioFile)
}

@AudioPlayerScope
class AudioInteractorImpl @Inject constructor(
        private val audioLibWrapper: AudioLibWrapper,
        deviceManager: DeviceManager
) : AudioInteractor {

    init {
        val (sampleRate, bufferSize) = deviceManager.getDeviceAudioInfo()
        audioLibWrapper.initialize(sampleRate, bufferSize)
    }


    override fun play(audioFile: AudioFile) {
        Timber.i("play $audioFile")
        audioLibWrapper.playAudioFile(audioFile.pathToFile, 0, File(audioFile.pathToFile).length().toInt())
    }
}