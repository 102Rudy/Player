package com.rygital.player.explorer.presentation

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.audioplayer.domain.AudioInteractor
import com.rygital.core.model.AudioFile
import com.rygital.core.system.ResourceManager
import com.rygital.core.utils.UiEvent
import com.rygital.player.explorer.R
import com.rygital.player.explorer.domain.ExplorerInteractor
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

class ExplorerViewModel(
        private val explorerInteractor: ExplorerInteractor,
        private val audioInteractor: AudioInteractor,
        private val resourceManager: ResourceManager
) : ViewModel() {

    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE: String = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 1000
    }

    private val _audioFiles = MutableLiveData<List<AudioFile>>()
    val audioFiles: LiveData<List<AudioFile>>
        get() = _audioFiles

    private val _showRequestPermissionDialog = MutableLiveData<UiEvent<Pair<String, Int>>>()
    val showRequestPermissionDialog: LiveData<UiEvent<Pair<String, Int>>>
        get() = _showRequestPermissionDialog

    private val _showPermissionExplanationDialog = MutableLiveData<UiEvent<PermissionDialogExplanationViewData>>()
    val showPermissionExplanationDialog: LiveData<UiEvent<PermissionDialogExplanationViewData>>
        get() = _showPermissionExplanationDialog

    init {
        Timber.i("view model $this")
    }

    fun getAudioFiles() {
        _showRequestPermissionDialog.value =
                UiEvent(PERMISSION_READ_EXTERNAL_STORAGE to PERMISSIONS_REQUEST_READ_STORAGE)
    }

    fun playAudioFile(audioFile: AudioFile) {
        audioInteractor.play(audioFile)
    }

    private fun loadAudioFilesFromStorage() {
//        _audioFiles.postValue(explorerInteractor.getSongs())
    }

    /// region Permission methods
    fun onPermissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_STORAGE -> loadAudioFilesFromStorage()
        }
    }

    fun onPermissionDenied(requestCode: Int) {
        val permission = when (requestCode) {
            PERMISSIONS_REQUEST_READ_STORAGE -> PERMISSION_READ_EXTERNAL_STORAGE
            else -> return
        }

        onShouldShowPermissionExplanation(permission, requestCode, afterDenied = true)
    }

    fun onShouldShowPermissionExplanation(permission: String, requestCode: Int, afterDenied: Boolean = false) {
        val dialogTitle = resourceManager.getString(
                if (afterDenied) R.string.permission_read_explanation_title_after_denied
                else R.string.permission_read_explanation_title
        )
        val message = resourceManager.getString(
                when (requestCode) {
                    PERMISSIONS_REQUEST_READ_STORAGE -> R.string.permission_read_explanation
                    else -> return
                }
        )
        val positiveText = resourceManager.getString(R.string.permission_read_explanation_retry)
        val negativeText = resourceManager.getString(R.string.permission_read_explanation_close)

        _showPermissionExplanationDialog.value = UiEvent(
                PermissionDialogExplanationViewData(
                        dialogTitle, message, negativeText, positiveText, permission, requestCode
                )
        )
    }
    /// endregion
}