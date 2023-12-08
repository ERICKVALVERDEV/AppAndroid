package com.evalverde.appintegration.components

import com.evalverde.appintegration.components.HttpNetwork.Companion.GetApiServices
import com.evalverde.appintegration.globalModel.JsonBrokenBody
import com.evalverde.appintegration.globalModel.ResponseBroker
import com.evalverde.appintegration.globalModel.adapters.LocalDateAdapter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class BrokerConnector {

    suspend inline fun <reified T> DoBusinessOperation(containerKey: String, targetComponent: String,
        method: String, vararg arguments: Any? ): T {
        try {
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                .create()
            var convertArguments = gson.toJson(arguments)
            println("ESTO ENVIA "+convertArguments)
            val resquest = JsonBrokenBody("", containerKey, targetComponent, method, convertArguments)
            return DoJsonBrokerOperation(resquest);
        }catch (ex: Exception) {
            throw ex
        }
    }

    suspend inline fun <reified T> DoJsonBrokerOperation(request: JsonBrokenBody
    ): T {
        val gson = Gson()
        var bodyString: String
        try {
            val body = GetApiServices().DoBusinessOperation(request)

            if (body!!.Exception == null) {
                    bodyString = body.JsonResponse
                }else{
                    var except = body.Exception
                    throw Exception(except.Message)
                }

            return gson.fromJson(bodyString, T::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
            println("SE CAYO AQUI")
            throw ex
        }
    }
}