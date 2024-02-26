package com.example.quizapp.ui.presentation.search_page

import com.example.quizapp.feartures.domain.model.WordInfo

sealed class SearchState{
    data object Loading: SearchState()
    data class Success(val data: List<WordInfo>): SearchState()
    data class Error(val error: String): SearchState()
}