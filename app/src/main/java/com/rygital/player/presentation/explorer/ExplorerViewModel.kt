package com.rygital.player.presentation.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.audioplayer.domain.AudioInteractor
import com.rygital.player.domain.AudioFile
import com.rygital.player.domain.ExplorerInteractor
import timber.log.Timber
import javax.inject.Inject


class ExplorerViewModelFactory @Inject constructor(
        private val explorerInteractor: ExplorerInteractor,
        private val audioInteractor: AudioInteractor
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ExplorerViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return ExplorerViewModel(explorerInteractor, audioInteractor) as T
        }
        throw IllegalArgumentException("Unknown model class: $modelClass")
    }
}

class ExplorerViewModel(
        private val explorerInteractor: ExplorerInteractor,
        private val audioInteractor: AudioInteractor
) : ViewModel() {

    init {
        Timber.i("view model $this")
    }

    fun getAudioFiles(): List<AudioFile> = explorerInteractor.getSongs()

}
