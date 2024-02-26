package com.example.quizapp.ui.presentation.favorite_page

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.feartures.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCases: WordUseCases
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _favoriteWords = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val favoriteWords: MutableStateFlow<FavoriteState> = _favoriteWords

    init {
        fetchFavoriteWords()
    }

    private fun fetchFavoriteWords() {
        _favoriteWords.value = FavoriteState.Loading
        scope.launch {
            try{
                val result = useCases.getFavoriteWords.invoke()
                result.collectLatest {
                    when(it){
                        is Resource.Success -> {
                           _favoriteWords.value = FavoriteState.Success(it.data ?: emptyList())
                        }
                        is Resource.Error -> {
                            _favoriteWords.value = FavoriteState.Error(it.message ?: "An unexpected error occurred")
                        }
                        is Resource.Loading -> {
                            _favoriteWords.value = FavoriteState.Loading
                        }
                    }
                }
            } catch (e: Exception){
                _favoriteWords.value = FavoriteState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }


}