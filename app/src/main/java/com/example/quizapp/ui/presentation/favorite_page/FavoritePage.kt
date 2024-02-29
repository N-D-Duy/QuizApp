package com.example.quizapp.ui.presentation.favorite_page

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.ui.widgets.CustomLoading
import com.example.quizapp.ui.widgets.WordItem

@Composable
fun FavoritePage(
    viewModel: FavoriteViewModel,
    navigateToWordDetails: (WordInfo) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.fetchFavoriteWords()
    }
    val state = viewModel.favoriteWords.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is FavoriteState.Loading -> {
                CustomLoading()
            }

            is FavoriteState.Success -> {
                val data = (state.value as FavoriteState.Success).data
                if (data.isEmpty()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = "No favorite words")
                } else {
                    LazyColumn(content = {
                        items(data.size) {
                            WordItem(word = data[it], onClick = {
                                navigateToWordDetails(data[it])
                            })
                        }
                    })
                }
            }

            is FavoriteState.Error -> {
                Text(modifier = Modifier.align(Alignment.Center), text = "Error")
            }
        }
    }

}