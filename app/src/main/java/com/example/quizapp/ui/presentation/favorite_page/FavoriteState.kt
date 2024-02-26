package com.example.quizapp.ui.presentation.favorite_page

import com.example.quizapp.feartures.domain.model.WordInfo

sealed class FavoriteState{
    data object Loading: FavoriteState()
    data class Success(val data: List<WordInfo>): FavoriteState()
    data class Error(val error: String): FavoriteState()
}