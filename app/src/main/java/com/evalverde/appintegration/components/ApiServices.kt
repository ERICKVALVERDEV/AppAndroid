package com.evalverde.appintegration.components

import com.evalverde.appintegration.globalModel.JsonBrokenBody
import com.evalverde.appintegration.globalModel.ResponseBroker
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Objects

interface ApiServices {
    @POST("/coreServices/json/businessOperation/")
    suspend fun DoBusinessOperation(@Body body: JsonBrokenBody?): ResponseBroker

    @GET("post/{id}")
    suspend fun getPost(@Path("id") postId:Int):POST
}