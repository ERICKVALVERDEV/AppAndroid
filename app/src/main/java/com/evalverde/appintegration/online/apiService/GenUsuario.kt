package com.evalverde.appintegration.online.apiService

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GenUsuario {

    @GET("post/{id}")
    suspend fun getPost(@Path("id") postId:Int):POST
}