package com.atb.englishdictionary.domain.use_case.delete_words

import com.atb.englishdictionary.domain.repository.DictionaryRepository

class DeleteAllWordUseCase(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllWords()
    }
}