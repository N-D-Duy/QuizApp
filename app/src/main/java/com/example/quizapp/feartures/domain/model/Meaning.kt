package com.example.quizapp.feartures.domain.model

import java.io.Serializable

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String? = ""
    //smt else here
): Serializable