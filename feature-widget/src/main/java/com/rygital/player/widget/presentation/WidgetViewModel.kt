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
import kotlinx.coroutines.channels.consumeEach
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

    private val _audioFile = MutableLiveData<AudioFile>()
    val audioFile: LiveData<AudioFile>
        get() = _audioFile

    private val _playerState = MutableLiveData<PlayerState>()
    val playerState: LiveData<PlayerState>
        get() = _playerState

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
    }

    fun togglePlayPause() {
        launch {
            if (playerState.value == PlayerState.PAUSED) {
                audioInteractor.play()
            } else {
                audioInteractor.pause()
            }
        }
    }
}