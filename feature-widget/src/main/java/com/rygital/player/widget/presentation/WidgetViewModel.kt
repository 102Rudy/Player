package com.rygital.player.widget.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.core.domain.AudioInteractor
import com.rygital.core.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
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

    private val _playerState = MutableLiveData<WidgetViewData>()
    val playerState: LiveData<WidgetViewData>
        get() = _playerState

    init {
        launch(Dispatchers.IO) {
            @Suppress("EXPERIMENTAL_API_USAGE")
            audioInteractor.currentAudioFileChannel.consumeEach {
                _playerState.postValue(WidgetViewData(it.title, true))
            }
        }
    }

    fun togglePlayPause() {
        audioInteractor.pause()
//        if (playerState.value?.isPlaying == true) {
//            audioInteractor.play()
//        } else {
//            audioInteractor.stop()
//        }
    }
}