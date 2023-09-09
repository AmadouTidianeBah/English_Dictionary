package com.atb.englishdictionary.data.repository

import android.util.Log
import com.atb.englishdictionary.core.Resource
import com.atb.englishdictionary.data.local.DictionaryDao
import com.atb.englishdictionary.data.remote.DictionaryApi
import com.atb.englishdictionary.domain.model.Word
import com.atb.englishdictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException

class DictionaryRepositoryImpl(
    private val dao: DictionaryDao,
    private val api: DictionaryApi
) : DictionaryRepository {
    override fun getWords(word: String): Flow<Resource<List<Word>>> = flow {
        emit(Resource.Loading())

        val localWords = dao.getWords(word).map { it.toWord() }
        emit(Resource.Loading(localWords))

        try {
            val remoteWords = api.getWord(word)
            dao.deleteWord(remoteWords.map { it.word })
            dao.insert(remoteWords.map { it.toDictionaryWordEntity() })
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach the server, check your connection!",
                data = localWords
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "An unexpected error occur!",
                data = localWords
            ))
        }

        val newLocalWords = dao.getWords(word).map { it.toWord() }
        emit(Resource.Success(newLocalWords))
    }

    override suspend fun getAllWords(): List<String> {
        return dao.getAllWords()
    }

    override suspend fun deleteAllWords() {
        dao.deleteAllWords()
    }

    override suspend fun getWordFromDb(word: String): List<Word> {
        return dao.getWords(word).map { it.toWord() }
    }
}