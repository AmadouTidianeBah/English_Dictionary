package com.atb.englishdictionary.domain.model

data class Word(
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String
)
