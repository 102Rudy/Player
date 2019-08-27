package com.rygital.player.explorer.presentation

data class PermissionDialogExplanationViewData(
        val dialogTitle: String,
        val dialogMessage: String,
        val negativeText: String,
        val positiveText: String,
        val permission: String,
        val requestCode: Int
)