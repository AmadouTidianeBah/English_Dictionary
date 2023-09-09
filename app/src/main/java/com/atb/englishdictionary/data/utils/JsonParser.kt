package com.atb.englishdictionary.data.utils

import java.lang.reflect.Type

interface JsonParser {
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(data: T, type: Type): String?
}