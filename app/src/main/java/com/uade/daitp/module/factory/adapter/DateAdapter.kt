package com.uade.daitp.module.factory.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    @FromJson
    fun fromJson(reader: JsonReader): Date {
        val dateString = reader.nextString()
        val result: Date? = if (dateString.contains("T")) {
            timeFormat.parse(dateString)
        } else {
            dateFormat.parse(dateString)
        }
        return result ?: Calendar.getInstance().time
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Date) {
        val dateString = dateFormat.format(value)
        writer.value(dateString)
    }
}