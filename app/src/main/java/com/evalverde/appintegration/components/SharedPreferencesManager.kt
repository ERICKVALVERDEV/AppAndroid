package com.evalverde.appintegration.components

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPreferencesIPSP",
        Context.MODE_PRIVATE)
    private val gson = Gson()
    val LOGIN_KEY = "LOGIN_KEY_SINGLE"

    fun saveLogin(login: List<Map<String,String>>){
        val jsonString = gson.toJson(login)
        val editor = sharedPreferences.edit().putString(LOGIN_KEY,jsonString)
        val commitResult = editor.commit()
        if (commitResult) {
            println("SharedPreferences Se guardó correctamente.")
        } else {
            println("SharedPreference Error al guardar.")
        }
    }

    fun obtenerLoginSingle():List<Map<String,String>>{
        val jsonString = sharedPreferences.getString(LOGIN_KEY,null)
        val tipo = object : TypeToken<List<Map<String, String>>>(){}.type
        return if(jsonString != null){
            gson.fromJson(jsonString,tipo)
        }else{
            emptyList()
        }
    }

}