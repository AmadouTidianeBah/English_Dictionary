package com.atb.englishdictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.atb.englishdictionary.data.remote.dto.MeaningDto
import com.atb.englishdictionary.data.utils.JsonParser
import com.atb.englishdictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromMeaningJson(json: String): List<MeaningDto> {
        return jsonParser.fromJson<ArrayList<MeaningDto>>(
            json,
            object: TypeToken<ArrayList<MeaningDto>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(meaning: List<MeaningDto>): String {
        return jsonParser.toJson(
            meaning,
            object: TypeToken<ArrayList<MeaningDto>>(){}.type
        ) ?: "[]"
    }
}