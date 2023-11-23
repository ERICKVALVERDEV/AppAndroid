package com.evalverde.appintegration.components

import com.google.gson.GsonBuilder
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val URL_BASE: String = ""
lateinit var API_SERVICE:ApiServices
class HttpNetwork{
    companion object{
        @Provides
        fun getRetrofit(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(URL_BASE)
                .build()
        }
        @Provides
        fun GetApiServices():ApiServices {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            if(API_SERVICE == null){

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                API_SERVICE = retrofit.create(ApiServices::class.java)
            }
            return API_SERVICE
        }
    }
}
