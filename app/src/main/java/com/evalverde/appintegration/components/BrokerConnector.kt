package com.evalverde.appintegration.components

import com.evalverde.appintegration.components.HttpNetwork.Companion.GetApiServices
import com.evalverde.appintegration.globalModel.JsonBrokenBody
import com.evalverde.appintegration.globalModel.adapters.DateDeserializer
import com.evalverde.appintegration.globalModel.adapters.LocalDateAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.util.Date

class BrokerConnector {

    suspend inline fun <reified T> DoBusinessOperation(containerKey: String, targetComponent: String,
        method: String, vararg arguments: Any? ): T {
        try {
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                .create()
            var convertArguments = gson.toJson(arguments)
//            println("ESTO ENVIA "+convertArguments)
            val resquest = JsonBrokenBody("", containerKey, targetComponent, method, convertArguments)
            return DoJsonBrokerOperation(resquest);
        }catch (ex: Exception) {
            throw ex
        }
    }

    suspend inline fun <reified T> DoJsonBrokerOperation(request: JsonBrokenBody
    ): T {
        val gson = GsonBuilder()
        var bodyString: String
        try {
            val body = GetApiServices().DoBusinessOperation(request)

            if (body!!.Exception == null) {
                    bodyString = body.JsonResponse
                }else{
                    var except = body.Exception
                    throw Exception(except.Message)
                }
            val tipo = object : TypeToken<T>() {}.type
            return gson
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .create()
                .fromJson(bodyString, tipo)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}