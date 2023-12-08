package com.evalverde.appintegration.components

import android.app.Activity
import android.app.AlertDialog
import com.evalverde.appintegration.R

class LoadingDialog constructor(val activity: Activity) {
    private lateinit var dialog: AlertDialog
    fun startLoadingDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog,null))
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
    }
    fun dimissDialog(){
        dialog.dismiss()
    }

}