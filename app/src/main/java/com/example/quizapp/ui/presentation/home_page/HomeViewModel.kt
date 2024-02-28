package com.example.quizapp.ui.presentation.home_page

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.enums.UpdateWordField
import com.example.quizapp.core_utils.functions.ReadWordsFromAsset
import com.example.quizapp.feartures.domain.model.WordInfo
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

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite

    init {
        fetchRandomWord()
    }
    fun isFavoriteWord(word: String){
        scope.launch {
            val result = useCases.isFavorite.invoke(word)
            result.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _isFavorite.value = false
                    }
                    is Resource.Success -> {
                        _isFavorite.value = it.data ?: false
                    }
                    is Resource.Error -> {
                        _isFavorite.value = false
                    }
                }
            }
        }
    }

    private val _downloadWordsState = MutableStateFlow<DownloadWords>(
        DownloadWords.Loading
    )
    val downloadWordsState: MutableStateFlow<DownloadWords> = _downloadWordsState


    fun downloadWords(context: Context) {
        _downloadWordsState.value = DownloadWords.Loading
        scope.launch {
            try {
                val stringWords = ReadWordsFromAsset.readWordsFromAsset(context)
                val result = useCases.downloadWordsFromApi.invoke(stringWords)

                result.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            _downloadWordsState.value = DownloadWords.Loading
                        }

                        is Resource.Success -> {
                            _downloadWordsState.value =
                                DownloadWords.DownloadWordsSuccess(
                                    it.data ?: emptyList()
                                )
                        }

                        is Resource.Error -> {
                            _downloadWordsState.value =
                                DownloadWords.DownloadWordsError(
                                    it.message ?: "An unexpected error occurred"
                                )
                        }
                    }
                }

            } catch (e: Exception) {
                _downloadWordsState.value =
                    DownloadWords.DownloadWordsError(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun fetchRandomWord() {
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
                            _dataState.value =
                                HomeState.Error(it.message ?: "An unexpected error occurred")
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


    sealed class DownloadWords {
        data object Loading : DownloadWords()
        data class DownloadWordsSuccess(val words: List<WordInfo>) : DownloadWords()
        data class DownloadWordsError(val message: String) : DownloadWords()
    }
}