package com.evalverde.appintegration.components

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun DisplayAlert(context: Context, title: String, message: String): AlertDialog.Builder {
    return AlertDialog.Builder(context)
    .setTitle(title)
    .setMessage(message)
}
fun DisplayAlert(context: Context, title: String, message: String, positiveButton: String){
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .show()
}