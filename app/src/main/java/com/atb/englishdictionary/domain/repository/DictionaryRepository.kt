package com.atb.englishdictionary.domain.repository

import com.atb.englishdictionary.core.Resource
import com.atb.englishdictionary.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun getWords(word: String): Flow<Resource<List<Word>>>
    suspend fun getAllWords(): List<String>
    suspend fun deleteAllWords()
    suspend fun getWordFromDb(word: String): List<Word>
}