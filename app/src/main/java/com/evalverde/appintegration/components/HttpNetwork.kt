package com.evalverde.appintegration.components

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val URL_BASE: String = "http://172.16.11.20:9095/"
lateinit var API_SERVICE:ApiServices
@Module
@InstallIn(SingletonComponent::class)
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

                val gson = GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Call::class.java, CallTypeAdapter())
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}
