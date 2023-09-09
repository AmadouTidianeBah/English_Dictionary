package com.atb.englishdictionary.data.remote.dto

import com.atb.englishdictionary.domain.model.Meaning

data class MeaningDto(
    val antonyms: List<String>,
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    val synonyms: List<String>
) {
    fun toMeaning(): Meaning {
        return Meaning(antonyms, definitions.map { it.toDefinition() }, partOfSpeech, synonyms)
    }
}