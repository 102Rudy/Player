package com.rygital.audioplayer.domain

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.di.AudioPlayerScope
import com.rygital.audioplayer.system.DeviceManager
import com.rygital.core.domain.AudioInteractor
import com.rygital.core.model.AudioFile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import java.io.File
import javax.inject.Inject

internal interface AudioRepository {
    fun getCurrentPlaylist(): List<Song>
}

@AudioPlayerScope
internal class AudioInteractorImpl @Inject constructor(
        private val audioLibWrapper: AudioLibWrapper,
        private val deviceManager: DeviceManager
) : AudioInteractor {

    private val _currentAudioFileChannel: Channel<AudioFile> = Channel()
    override val currentAudioFileChannel: ReceiveChannel<AudioFile>
        get() = _currentAudioFileChannel

    override fun initialize() {
        audioLibWrapper.loadLibrary()

        val (sampleRate, bufferSize) = deviceManager.getDeviceAudioInfo()
        audioLibWrapper.initialize(sampleRate, bufferSize)
    }

    override fun onBackground() {
        audioLibWrapper.onBackground()
    }

    override fun onForeground() {
        audioLibWrapper.onForeground()
    }

    override suspend fun open(audioFile: AudioFile) {
        Timber.i("open audio file: $audioFile")
        audioLibWrapper.openAudioFile(audioFile.pathToFile, 0, File(audioFile.pathToFile).length().toInt())

        _currentAudioFileChannel.send(audioFile)
    }

    override fun play() {
        audioLibWrapper.play()
    }

    override fun pause() {
        audioLibWrapper.pause()
    }

    override fun stop() {

    }
}