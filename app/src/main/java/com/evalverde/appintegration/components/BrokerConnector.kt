package com.evalverde.appintegration.components

import com.evalverde.appintegration.components.HttpNetwork.Companion.GetApiServices
import com.evalverde.appintegration.globalModel.JsonBrokenBody
import com.evalverde.appintegration.globalModel.ResponseBroker

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrokerConnector {

    suspend inline fun <reified T> DoBusinessOperation(containerKey: String, targetComponent: String,
        method: String, vararg arguments: Any ): T {
        try {
            val resquest = JsonBrokenBody("", containerKey, targetComponent, method, Gson().toJson(arguments))
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
            throw ex
        }
    }
}