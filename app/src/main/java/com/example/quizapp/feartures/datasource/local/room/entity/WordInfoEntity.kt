package com.example.quizapp.feartures.datasource.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.quizapp.core_utils.enums.DismissDuration
import com.example.quizapp.feartures.domain.model.Meaning
import com.example.quizapp.feartures.domain.model.Phonetic
import com.example.quizapp.feartures.domain.model.WordInfo

@Entity(tableName = "word-table")
data class WordInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var word: String,
    var phonetic: String? = "",
    var phonetics: List<Phonetic>? = emptyList(),
    var origin: String? = "",
    var meanings: List<Meaning>,
    var isFavorite: Boolean = false,
    var isUsed: Boolean = false,
    var dismissDuration: DismissDuration? = null,
    var expiredTime: Long? = null, //thời gian hết hạn
    var isSkipped: Byte = 0,
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word, 
            phonetic = phonetic,
            phonetics = phonetics,
            origin = origin,
            isFavorite = isFavorite,
            isUsed = isUsed,
            dismissDuration = dismissDuration,
            expiredTime = expiredTime,
            isSkipped = isSkipped,
        )
    }
}