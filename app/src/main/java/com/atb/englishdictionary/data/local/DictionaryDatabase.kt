package com.atb.englishdictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.atb.englishdictionary.data.local.entity.WordEntity

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract val dao: DictionaryDao

    companion object {
        const val NAME = "dictionary_db"
    }
}