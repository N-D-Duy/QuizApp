package com.example.quizapp.ui.presentation.home_page

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.enums.UpdateWordField
import com.example.quizapp.feartures.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: WordUseCases
) : ViewModel() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _dataState = MutableStateFlow<HomeState>(HomeState.Loading)
    val dataState: StateFlow<HomeState> = _dataState

    private val _isWordHidden =
        MutableStateFlow<HomeWidgetState>(HomeWidgetState.IsWordHidden) //word was hidden (false)
    val isWordHidden: StateFlow<HomeWidgetState> = _isWordHidden

    private val _isPlayingAudio =
        MutableStateFlow<HomeWidgetState>(HomeWidgetState.IsPausedAudio) //audio is paused (false)
    val isPlayingAudio: StateFlow<HomeWidgetState> = _isPlayingAudio

    private val _isShowingOptionBar =
        MutableStateFlow<HomeWidgetState>(HomeWidgetState.IsHidingOptionBar) //option bar is hidden (false)
    val isShowingOptionBar: StateFlow<HomeWidgetState> = _isShowingOptionBar

    private val _isFavoritePressed =
        MutableStateFlow<HomeWidgetState>(HomeWidgetState.IsFavoriteUnPressed) //favorite is not pressed (false)
    val isFavoritePressed: StateFlow<HomeWidgetState> = _isFavoritePressed

    private val _isDescriptionShowing =
        MutableStateFlow<HomeWidgetState>(HomeWidgetState.IsDescriptionHiding) //description is hidden (false)
    val isDescriptionShowing: StateFlow<HomeWidgetState> = _isDescriptionShowing

    init {
        fetchRandomWord()
    }

    private fun fetchRandomWord() {
        _dataState.value = HomeState.Loading
        scope.launch {
            try {
                val result = useCases.fetchRandomUnusedWordsFromWordTable.invoke()
                result.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            _dataState.value = HomeState.Loading
                        }
                        is Resource.Success -> {
                            _dataState.value = HomeState.Success(it.data ?: emptyList())
                        }
                        is Resource.Error -> {
                            _dataState.value = HomeState.Error(it.message ?: "An unexpected error occurred")
                        }
                    }
                }
            } catch (e: Exception) {
                _dataState.value = HomeState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun updateWord(word: String, data: Boolean, type: UpdateWordField) {
        scope.launch {
            useCases.updateWord.invoke(word, data, type)
        }
    }

    fun updateWordState(isHidden: Boolean) {
        if (isHidden) {
            _isWordHidden.value = HomeWidgetState.IsWordHidden
        } else {
            _isWordHidden.value = HomeWidgetState.IsWordShowing
        }
    }

    fun updateAudioState(isPlaying: Boolean) {
        if (isPlaying) {
            _isPlayingAudio.value = HomeWidgetState.IsPlayingAudio
        } else {
            _isPlayingAudio.value = HomeWidgetState.IsPausedAudio
        }
    }

    fun updateOptionBarState(isShowing: Boolean) {
        if (isShowing) {
            _isShowingOptionBar.value = HomeWidgetState.IsShowingOptionBar
        } else {
            _isShowingOptionBar.value = HomeWidgetState.IsHidingOptionBar
        }
    }

    fun updateFavoritePressedState(onPress: () -> Unit) {
        if (_isFavoritePressed.value is HomeWidgetState.IsFavoritePressed) {
            onPress() //update database
            _isFavoritePressed.value = HomeWidgetState.IsFavoriteUnPressed
        } else {
            onPress() //update database
            _isFavoritePressed.value = HomeWidgetState.IsFavoritePressed
        }
    }

    fun updateDescriptionState(isShowing: Boolean) {
        if (isShowing) {
            _isDescriptionShowing.value = HomeWidgetState.IsDescriptionShowing
        } else {
            _isDescriptionShowing.value = HomeWidgetState.IsDescriptionHiding
        }
    }
}

sealed class HomeWidgetState {
    data object IsShowingOptionBar : HomeWidgetState()
    data object IsHidingOptionBar : HomeWidgetState()

    data object IsPlayingAudio : HomeWidgetState()
    data object IsPausedAudio : HomeWidgetState()

    data object IsWordHidden : HomeWidgetState()
    data object IsWordShowing : HomeWidgetState()

    data object IsFavoritePressed : HomeWidgetState()
    data object IsFavoriteUnPressed : HomeWidgetState()

    data object IsDescriptionShowing : HomeWidgetState()
    data object IsDescriptionHiding : HomeWidgetState()
}