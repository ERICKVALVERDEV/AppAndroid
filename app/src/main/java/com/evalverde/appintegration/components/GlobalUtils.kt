package com.evalverde.appintegration.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun IsConnectivityNetwork(contexto: Context): Boolean {
    val gestorConectividad = contexto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val redActiva = gestorConectividad.activeNetwork
    if (redActiva != null) {
        val capacidadesRed = gestorConectividad.getNetworkCapabilities(redActiva)
        return capacidadesRed?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    return false
}