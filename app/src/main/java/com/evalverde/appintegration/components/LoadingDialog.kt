package com.evalverde.appintegration.components

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.evalverde.appintegration.R

class LoadingDialog constructor(val activity: Activity) {
    private lateinit var dialog: AlertDialog
    fun startLoadingDialog(text: String){

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        var inlfateLayout = inflater.inflate(R.layout.custom_dialog,null)
        var txtDescrp = inlfateLayout.findViewById<TextView>(R.id.textoDescriptivo)
        txtDescrp.text = "${text}..."
        builder.setView(inlfateLayout)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }
    fun dimissDialog(){
        dialog.dismiss()
    }

}