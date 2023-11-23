package com.evalverde.appintegration.components

import com.evalverde.appintegration.components.HttpNetwork.Companion.GetApiServices
import com.evalverde.appintegration.globalModel.JsonBrokenBody
import com.evalverde.appintegration.globalModel.ResponseObject

import com.google.gson.Gson
import retrofit2.Response

class BrokerConnector{

    suspend inline fun <reified T> DoBusinessOperation(containerKey:String, targetComponent: String, method: String, vararg arguments: Any):T{
        val resquest = JsonBrokenBody("", containerKey, targetComponent, method, Gson().toJson(arguments))
        return DoJsonBrokerOperation("DoBusinessOperation",  resquest);
    }

    suspend inline fun<reified T> DoJsonBrokerOperation(action: String,  request: JsonBrokenBody):T{
        val gson = Gson()
        try {
            val call: Response<ResponseObject> = GetApiServices().DoBusinessOperation(request)
            if(call.isSuccessful()){
                val resonseContent = call.body()?.toString()
                val responseData = gson.fromJson(resonseContent,ResponseObject::class.java)
                if(responseData != null){
                    return gson.fromJson(responseData.JsonResponse,T::class.java)
                }
            }else{
                throw Exception("La llamada no emitió una respuesta.")
            }
        }catch (ex : Exception){
            ex.printStackTrace()
            throw ex
        }

        throw Exception("Hubo un problema durante la operación")
    }
}