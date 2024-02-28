package com.example.quizapp.ui.presentation.search_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchPage(
    viewModel: SearchViewModel,
) {
    val state = viewModel.searchState.collectAsState()

    val query = remember {
        mutableStateOf(
            viewModel.query.value
        )
    }

    val isSearching = remember {
        mutableStateOf(
            viewModel.isSearching.value
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        TextField(
            value = query.value,
            onValueChange = {
                query.value = it.trim()
                viewModel.query.value = it
                viewModel.onSearchWordsChange()
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                if (isSearching.value) {
                    // Loading indicator
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        )
        // Search results
        when (state.value) {
            is SearchState.Loading -> {
                if(isSearching.value){
                    Text("Loading...")
                }
            }
            is SearchState.Success -> {
                val data = (state.value as SearchState.Success).data
                data.isNotEmpty().let { isNotEmpty ->
                    if (isNotEmpty) {
                        data.forEach {
                            Text(it.word)
                        }
                    } else {
                        Text("No results found")
                    }
                }
            }
            is SearchState.Error -> {
                val error = (state.value as SearchState.Error).error
                Text(error)
            }
        }
    }

}