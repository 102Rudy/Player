package com.rygital.core.presentation

import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rygital.core.R
import com.rygital.core.system.ResourceManager
import com.rygital.core.utils.UiEvent

interface PermissionDelegate {
    val showPermissionExplanationDialog: LiveData<UiEvent<PermissionDialogExplanationViewData>>
    val showRequestPermissionDialog: LiveData<UiEvent<Pair<String, Int>>>

    fun onPermissionGranted(requestCode: Int)
    fun onPermissionDenied(requestCode: Int)
    fun onShouldShowPermissionExplanation(permission: String, requestCode: Int, afterDenied: Boolean = false)
}

class PermissionData(
        val permission: String,
        val permissionExplanation: String,
        var onPermissionGrantedCallback: (() -> Unit)? = null
)

data class PermissionDialogExplanationViewData(
        val dialogTitle: String,
        val dialogMessage: String,
        val negativeText: String,
        val positiveText: String,
        val permission: String,
        val requestCode: Int
)

class PermissionDelegateImpl(
        private val resourceManager: ResourceManager,
        private val permissions: SparseArray<PermissionData>
) : PermissionDelegate {

    private val _showPermissionExplanationDialog = MutableLiveData<UiEvent<PermissionDialogExplanationViewData>>()
    override val showPermissionExplanationDialog: LiveData<UiEvent<PermissionDialogExplanationViewData>>
        get() = _showPermissionExplanationDialog

    private val _showRequestPermissionDialogMutable = MutableLiveData<UiEvent<Pair<String, Int>>>()
    override val showRequestPermissionDialog: LiveData<UiEvent<Pair<String, Int>>>
        get() = _showRequestPermissionDialogMutable

    fun setPermissionGrantedCallback(requestCode: Int, callback: () -> Unit) {
        permissions[requestCode].onPermissionGrantedCallback = callback
    }

    fun showRequestPermissionDialog(requestCode: Int) {
        _showRequestPermissionDialogMutable.value =
                UiEvent(permissions[requestCode].permission to requestCode)
    }

    override fun onPermissionGranted(requestCode: Int) {
        permissions[requestCode].onPermissionGrantedCallback?.invoke()
    }

    override fun onPermissionDenied(requestCode: Int) {
        val permission = permissions[requestCode].permission
        onShouldShowPermissionExplanation(permission, requestCode, afterDenied = true)
    }

    override fun onShouldShowPermissionExplanation(permission: String, requestCode: Int, afterDenied: Boolean) {
        val dialogTitle = resourceManager.getString(
                if (afterDenied) R.string.permission_explanation_title_after_denied
                else R.string.permission_explanation_title
        )
        val message = permissions[requestCode].permissionExplanation

        val positiveText = resourceManager.getString(R.string.permission_explanation_retry)
        val negativeText = resourceManager.getString(R.string.permission_explanation_close)

        _showPermissionExplanationDialog.value = UiEvent(
                PermissionDialogExplanationViewData(
                        dialogTitle, message, negativeText, positiveText, permission, requestCode
                )
        )
    }
}