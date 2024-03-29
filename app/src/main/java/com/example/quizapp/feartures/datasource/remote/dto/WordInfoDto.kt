package com.example.quizapp.feartures.datasource.remote.dto

import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String? = "",
    val phonetic: String? = "",
    val phonetics: List<PhoneticDto>? = emptyList(),
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            phonetics = phonetics?.map { it.toPhonetic() },
            word = word,
            id = 0
        )
    }
}