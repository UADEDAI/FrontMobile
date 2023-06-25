package com.uade.daitp.presentation.util

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R

fun Fragment.successDialog(title: String? = null, callback: (() -> Unit)? = null) {
    val sureTitle: String = title?.let { return@let title } ?: run {
        return@run getString(R.string.success)
    }
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(sureTitle)
        .setView(R.layout.dialog_success)
        .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
            callback?.invoke()
            dialog.dismiss()
        }
        .show()
}

fun Fragment.errorDialog(title: String? = null, callback: (() -> Unit)? = null) {
    val sureTitle: String = title?.let { return@let title } ?: run {
        return@run getString(R.string.error)
    }
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(sureTitle)
        .setView(R.layout.dialog_error)
        .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
            callback?.invoke()
            dialog.dismiss()
        }
        .show()
}