package com.example.quizapp.ui.presentation.home_page

import com.example.quizapp.feartures.domain.model.WordInfo

sealed class HomeState{
    data class Success(val data: List<WordInfo>): HomeState()
    data object Loading: HomeState()
    data class Error(val error: String): HomeState()
}