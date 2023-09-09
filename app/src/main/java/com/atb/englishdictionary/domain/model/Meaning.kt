package com.atb.englishdictionary.domain.model

import com.atb.englishdictionary.data.remote.dto.DefinitionDto

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
)
