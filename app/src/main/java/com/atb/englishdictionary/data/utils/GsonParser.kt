package com.atb.englishdictionary.data.utils

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
) : JsonParser {
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(data: T, type: Type): String {
        return gson.toJson(data, type)
    }
}