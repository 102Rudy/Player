package com.rygital.player.widget.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.core.domain.AudioInteractor
import com.rygital.core.model.AudioFile
import com.rygital.core.model.PlayerState
import com.rygital.core.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WidgetViewModelFactory @Inject constructor(
        private val audioInteractor: AudioInteractor
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == WidgetViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return WidgetViewModel(audioInteractor) as T
        }
        throw IllegalArgumentException("Unknown model class: $modelClass")
    }
}

class WidgetViewModel(
        private val audioInteractor: AudioInteractor
) : BaseViewModel() {

    companion object {
        private const val UPDATE_POSITION_INTERVAL: Long = 1000L
    }

    private val _audioFile = MutableLiveData<AudioFile>()
    val audioFile: LiveData<AudioFile>
        get() = _audioFile

    private val _playerState = MutableLiveData<PlayerState>()
    val playerState: LiveData<PlayerState>
        get() = _playerState

    private val _position = MutableLiveData<Float>()
    val position: LiveData<Float>
        get() = _position

    private var playAfterSeek: Boolean = true

    private var updatePositionJob: Job? = null

    init {
        launch(Dispatchers.IO) {
            @Suppress("EXPERIMENTAL_API_USAGE")
            audioInteractor.audioFileChannel.consumeEach {
                Timber.i("audioFileChannel, $it")
                _audioFile.postValue(it)
            }
        }

        launch(Dispatchers.IO) {
            @Suppress("EXPERIMENTAL_API_USAGE")
            audioInteractor.playerStateChannel.consumeEach {
                Timber.i("playerStateChannel, $it")
                _playerState.postValue(it)
            }
        }

        startUpdatingPosition()
    }

    fun togglePlayPause() {
        if (playerState.value == PlayerState.PAUSED) {
            play()
        } else {
            pause()
        }
    }

    fun seekTo(positionPercent: Double) {
        audioInteractor.seekTo(positionPercent)
    }

    fun setEnabledUpdatePosition(enabled: Boolean) {
        updatePositionJob?.cancel()
        updatePositionJob = null

        if (enabled) {
            if (playAfterSeek) {
                play()
            }
            startUpdatingPosition()
        } else {
            playAfterSeek = playerState.value == PlayerState.PLAYING
            pause()
        }
    }

    private fun play() {
        launch {
            audioInteractor.play()
        }
    }

    private fun pause() {
        launch {
            audioInteractor.pause()
        }
    }

    private fun startUpdatingPosition() {
        updatePositionJob = launch(Dispatchers.IO) {
            while (true) {
                delay(UPDATE_POSITION_INTERVAL)

                val pos = audioInteractor.getPositionPercent().toFloat()
                _position.postValue(pos)
            }
        }
    }
}