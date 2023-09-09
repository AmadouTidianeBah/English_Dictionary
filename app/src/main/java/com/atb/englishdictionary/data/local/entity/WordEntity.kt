package com.atb.englishdictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atb.englishdictionary.data.remote.dto.MeaningDto
import com.atb.englishdictionary.domain.model.Word

@Entity
data class WordEntity(
    val meanings: List<MeaningDto>,
    val phonetic: String?,
    val word: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    fun toWord(): Word {
        return Word(meanings.map { it.toMeaning() }, phonetic, word)
    }
}
