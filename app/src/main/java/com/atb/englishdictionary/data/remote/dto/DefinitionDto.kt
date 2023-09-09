package com.atb.englishdictionary.data.remote.dto

import com.atb.englishdictionary.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
) {
    fun toDefinition(): Definition {
        return Definition(antonyms, definition, example, synonyms)
    }
}