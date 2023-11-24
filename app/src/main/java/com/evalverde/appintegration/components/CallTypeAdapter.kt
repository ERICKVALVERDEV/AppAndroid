package com.evalverde.appintegration.components

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import retrofit2.Call

class CallTypeAdapter : TypeAdapter<Call<*>>() {
    override fun write(out: JsonWriter?, value: Call<*>?) {
        var suma = 5+5
        // Implementa la lógica de escritura si es necesario
    }

    override fun read(`in`: JsonReader?): Call<*>? {
        var suma = 5+5

        return null
    }
}
