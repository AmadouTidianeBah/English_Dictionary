package com.atb.englishdictionary.presentation.dictionary_home

import com.atb.englishdictionary.domain.model.Word

data class DictionaryWordState(
    val words: List<Word> = emptyList(),
    val isLoading: Boolean = false,
    val history: List<String> = emptyList()
)
