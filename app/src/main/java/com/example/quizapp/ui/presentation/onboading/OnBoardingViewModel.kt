package com.example.quizapp.ui.presentation.onboading

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.functions.ReadWordsFromAsset
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.feartures.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val useCases: WordUseCases
): ViewModel(){
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _event = MutableStateFlow<OnBoardingDataEvent>(OnBoardingDataEvent.Loading)
    val event: MutableStateFlow<OnBoardingDataEvent> = _event


    fun downloadWords(context: Context){
        _event.value = OnBoardingDataEvent.Loading
        scope.launch {
            try{
                val stringWords = ReadWordsFromAsset.readWordsFromAsset(context)
                val result = useCases.downloadWordsFromApi.invoke(stringWords)

                result.collectLatest {
                    when(it){
                        is Resource.Loading -> {
                            _event.value = OnBoardingDataEvent.Loading
                        }
                        is Resource.Success -> {
                            _event.value = OnBoardingDataEvent.DownloadWordsSuccess(it.data ?: emptyList())
                        }
                        is Resource.Error -> {
                            _event.value = OnBoardingDataEvent.DownloadWordsError(it.message ?: "An unexpected error occurred")
                        }
                    }
                }

            } catch (e: Exception){
                _event.value = OnBoardingDataEvent.DownloadWordsError(e.message ?: "An unexpected error occurred")
            }
        }
    }


    sealed class OnBoardingDataEvent{
        data object Loading: OnBoardingDataEvent()
        data class DownloadWordsSuccess(val words: List<WordInfo>): OnBoardingDataEvent()
        data class DownloadWordsError(val message: String): OnBoardingDataEvent()
    }
}

