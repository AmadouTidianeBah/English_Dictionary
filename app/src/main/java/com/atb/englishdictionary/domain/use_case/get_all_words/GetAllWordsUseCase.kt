package com.atb.englishdictionary.domain.use_case.get_all_words

import com.atb.englishdictionary.domain.repository.DictionaryRepository

class GetAllWordsUseCase(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getAllWords()
    }
}