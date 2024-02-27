package com.example.quizapp.ui.presentation.search_page

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
class SearchViewModel @Inject constructor(
    private val useCases: WordUseCases
): ViewModel(){
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _query = MutableStateFlow("")
    val query: MutableStateFlow<String> = _query

    private val _isSearching = MutableStateFlow(false)
    val isSearching: MutableStateFlow<Boolean> = _isSearching

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    val searchState: MutableStateFlow<SearchState> = _searchState

    fun onSearchWordsChange(){
        _searchState.value = SearchState.Loading
        scope.launch {
            try{
                val result = useCases.searchWord(query.value)
                result.collectLatest {
                    when(it){
                        is Resource.Loading -> {
                            _searchState.value = SearchState.Loading
                        }
                        is Resource.Success -> {
                            _searchState.value = SearchState.Success(it.data ?: emptyList())
                        }
                        is Resource.Error -> {
                            _searchState.value = SearchState.Error(it.message ?: "An unexpected error occurred")
                        }
                    }
                }
            } catch (e: Exception){
                _searchState.value = SearchState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}