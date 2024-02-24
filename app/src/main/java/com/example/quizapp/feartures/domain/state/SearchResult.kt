package com.example.quizapp.feartures.domain.state

import com.example.quizapp.feartures.domain.model.WordInfo

data class SearchResult(
    val listWord: List<WordInfo>? = null,
    val isLoading: Boolean = false,
)