package com.evalverde.appintegration.components

import com.evalverde.appintegration.online.apiService.GenUsuario
import dagger.Provides
import retrofit2.Retrofit

class HttpNetwork{
    val URL_BASE: String = ""
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .build()
    }
    @Provides
    fun provideApiServices(retrofit: Retrofit):GenUsuario {
        return retrofit.create(GenUsuario::class.java)
    }
}