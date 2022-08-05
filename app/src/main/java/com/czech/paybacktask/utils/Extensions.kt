package com.czech.paybacktask.utils

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.launchFragment(direction: NavDirections) = try {
    findNavController().navigate(direction)
} catch (e: Exception) {
    Log.e("NAVIGATION ERROR", e.message.toString())
}

fun Context.showDialog(message: String, positive: DialogInterface.OnClickListener?) {
    MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton("OK", positive)
        .show()
}

fun View.hide(onlyInvisible: Boolean = false) {
    this.visibility = if (onlyInvisible) View.INVISIBLE else View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.loadImage(url: String, container: ImageView) {
    Glide.with(this)
        .load(url)
        .into(container)
}