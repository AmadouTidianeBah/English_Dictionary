package com.atb.englishdictionary.data.remote.dto

import com.atb.englishdictionary.data.local.entity.WordEntity

data class WordDto(
    val license: License,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toDictionaryWordEntity(): WordEntity {
        return WordEntity(meanings, phonetic, word)
    }
}