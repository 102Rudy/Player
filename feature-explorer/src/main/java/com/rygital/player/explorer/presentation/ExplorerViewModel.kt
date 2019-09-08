package com.rygital.player.explorer.presentation

import android.Manifest
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.core.domain.AudioInteractor
import com.rygital.core.model.AudioFile
import com.rygital.core.presentation.BaseViewModel
import com.rygital.core.presentation.PermissionData
import com.rygital.core.presentation.PermissionDelegate
import com.rygital.core.presentation.PermissionDelegateImpl
import com.rygital.core.system.ResourceManager
import com.rygital.player.explorer.R
import com.rygital.player.explorer.domain.ExplorerInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class ExplorerViewModelFactory @Inject constructor(
        private val explorerInteractor: ExplorerInteractor,
        private val audioInteractor: AudioInteractor,
        private val resourceManager: ResourceManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ExplorerViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return ExplorerViewModel(explorerInteractor, audioInteractor, resourceManager) as T
        }
        throw IllegalArgumentException("Unknown model class: $modelClass")
    }
}

internal class ExplorerViewModel(
        private val explorerInteractor: ExplorerInteractor,
        private val audioInteractor: AudioInteractor,
        private val permissionDelegate: PermissionDelegateImpl
) : BaseViewModel(), PermissionDelegate by permissionDelegate {

    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE: String = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 1000
    }

    private val _audioFiles = MutableLiveData<List<AudioFile>>()
    val audioFiles: LiveData<List<AudioFile>>
        get() = _audioFiles

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    constructor(
            explorerInteractor: ExplorerInteractor,
            audioInteractor: AudioInteractor,
            resourceManager: ResourceManager
    ) : this(
            explorerInteractor,
            audioInteractor,
            PermissionDelegateImpl(
                    resourceManager,
                    SparseArray<PermissionData>().apply {
                        put(
                                PERMISSIONS_REQUEST_READ_STORAGE,
                                PermissionData(
                                        PERMISSION_READ_EXTERNAL_STORAGE,
                                        resourceManager.getString(R.string.permission_read_explanation)
                                )
                        )
                    })
    )

    init {
        permissionDelegate.setPermissionGrantedCallback(PERMISSIONS_REQUEST_READ_STORAGE, ::loadAudioFilesFromStorage)
    }

    fun getAudioFiles() {
        launch {
            _showProgress.value = true
            _audioFiles.value = withContext(Dispatchers.IO) { explorerInteractor.getSongs() }
            _showProgress.value = false
        }
    }

    fun refreshAudioFiles() {
        permissionDelegate.showRequestPermissionDialog(PERMISSIONS_REQUEST_READ_STORAGE)
    }

    private fun loadAudioFilesFromStorage() {
        launch {
            _showProgress.value = true
            _audioFiles.value = withContext(Dispatchers.IO) { explorerInteractor.refreshSongs() }
            _showProgress.value = false
        }
    }

    fun playAudioFile(audioFile: AudioFile) {
        launch(Dispatchers.IO) {
            audioInteractor.open(audioFile)
            audioInteractor.play()
        }
    }
}