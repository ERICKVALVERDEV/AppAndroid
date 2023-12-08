package com.evalverde.appintegration.globalModel.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class DateDeserializer: JsonDeserializer<Date> {
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        val dateAsString = json?.asString
        return try {
            dateFormat.parse(dateAsString)
        } catch (e: ParseException) {
            // Handle the exception appropriately
            Date()
        }
    }
}