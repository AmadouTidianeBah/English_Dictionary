package com.atb.englishdictionary.domain.use_case.get_words

import com.atb.englishdictionary.core.Resource
import com.atb.englishdictionary.domain.model.Word
import com.atb.englishdictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordsUseCase(
    private val repository: DictionaryRepository
) {
    operator fun invoke(word: String): Flow<Resource<List<Word>>> {
        return if (word.isBlank()) flow {  }
                else repository.getWords(word)
    }
}