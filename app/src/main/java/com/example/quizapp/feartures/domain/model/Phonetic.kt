package com.example.quizapp.feartures.domain.model

import java.io.Serializable

data class Phonetic(
    val audio: String? = "",
    val text: String? = ""
): Serializable