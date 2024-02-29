package com.example.quizapp.feartures.datasource.remote.dto

import com.example.quizapp.feartures.domain.model.Phonetic

data class PhoneticDto(
    val audio: String? = "",
    val text: String? = ""
){
    fun toPhonetic() = Phonetic(
        audio = audio,
        text = text
    )
}