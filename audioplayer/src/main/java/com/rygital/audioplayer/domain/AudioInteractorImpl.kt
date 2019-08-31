package com.rygital.audioplayer.domain

import com.rygital.audiolibrary.AudioLibWrapper
import com.rygital.audioplayer.di.AudioPlayerScope
import com.rygital.audioplayer.system.DeviceManager
import com.rygital.core.domain.AudioInteractor
import com.rygital.core.model.AudioFile
import com.rygital.core.model.PlayerState
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


    private val _audioFileChannel: Channel<AudioFile> = Channel()
    override val audioFileChannel: ReceiveChannel<AudioFile>
        get() = _audioFileChannel

    private val _playerStateChannel: Channel<PlayerState> = Channel()
    override val playerStateChannel: ReceiveChannel<PlayerState>
        get() = _playerStateChannel

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

        _audioFileChannel.send(audioFile)
    }

    override suspend fun play() {
        audioLibWrapper.play()
        _playerStateChannel.send(PlayerState.PLAYING)
    }

    override suspend fun pause() {
        audioLibWrapper.pause()
        _playerStateChannel.send(PlayerState.PAUSED)
    }

    override fun stop() {

    }
}