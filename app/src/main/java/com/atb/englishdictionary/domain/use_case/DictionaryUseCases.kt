package com.atb.englishdictionary.domain.use_case

import com.atb.englishdictionary.domain.use_case.delete_words.DeleteAllWordUseCase
import com.atb.englishdictionary.domain.use_case.get_all_words.GetAllWordsUseCase
import com.atb.englishdictionary.domain.use_case.get_words.GetWordsUseCase

data class DictionaryUseCases(
    val getWords: GetWordsUseCase,
    val getAllWords: GetAllWordsUseCase,
    val deleteAllWords: DeleteAllWordUseCase
)
