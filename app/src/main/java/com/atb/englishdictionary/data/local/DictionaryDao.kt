package com.atb.englishdictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atb.englishdictionary.data.local.entity.WordEntity

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(words: List<WordEntity>)

    @Query("DELETE FROM wordentity WHERE word IN(:words)")
    suspend fun deleteWord(words: List<String>)

    @Query("DELETE FROM wordentity")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM wordentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWords(word: String): List<WordEntity>

    @Query("SELECT word FROM wordentity")
    suspend fun getAllWords(): List<String>
}