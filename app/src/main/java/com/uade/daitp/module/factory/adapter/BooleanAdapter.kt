package com.uade.daitp.module.factory.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class BooleanAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Boolean {
        return when (reader.nextInt()) {
            0 -> false
            1 -> true
            else -> throw IllegalArgumentException("Invalid boolean value")
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Boolean) {
        writer.value(if (value) 1 else 0)
    }
}