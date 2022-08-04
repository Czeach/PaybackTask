package com.czech.paybacktask.utils

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.launchFragment(direction: NavDirections) = try {
    findNavController().navigate(direction)
} catch (e: Exception) {
    Log.e("NAVIGATION ERROR", e.message.toString())
}

fun Context.showDialog(message: String, positive: DialogInterface.OnClickListener?, negative: DialogInterface.OnClickListener?) {
    MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton("YES", positive)
        .setNegativeButton("CANCEL", negative)
        .show()
}