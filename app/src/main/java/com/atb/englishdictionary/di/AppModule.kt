package com.atb.englishdictionary.di

import android.app.Application
import androidx.room.Room
import com.atb.englishdictionary.data.local.Converter
import com.atb.englishdictionary.data.local.DictionaryDatabase
import com.atb.englishdictionary.data.remote.DictionaryApi
import com.atb.englishdictionary.data.repository.DictionaryRepositoryImpl
import com.atb.englishdictionary.data.utils.GsonParser
import com.atb.englishdictionary.domain.repository.DictionaryRepository
import com.atb.englishdictionary.domain.use_case.DictionaryUseCases
import com.atb.englishdictionary.domain.use_case.delete_words.DeleteAllWordUseCase
import com.atb.englishdictionary.domain.use_case.get_all_words.GetAllWordsUseCase
import com.atb.englishdictionary.domain.use_case.get_words.GetWordsUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DictionaryApi.BASE_URL)
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDictionaryDatabase(app: Application): DictionaryDatabase {
        return Room.databaseBuilder(
            app,
            DictionaryDatabase::class.java,
            DictionaryDatabase.NAME
        )
            .addTypeConverter(Converter(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(db: DictionaryDatabase, api: DictionaryApi): DictionaryRepository {
        return DictionaryRepositoryImpl(db.dao, api)
    }

    @Provides
    @Singleton
    fun provideDictionaryUseCases(repo: DictionaryRepository): DictionaryUseCases {
        return DictionaryUseCases(
            getWords = GetWordsUseCase(repo),
            getAllWords = GetAllWordsUseCase(repo),
            deleteAllWords = DeleteAllWordUseCase(repo)
        )
    }
}