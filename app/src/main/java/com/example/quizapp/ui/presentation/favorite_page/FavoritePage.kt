package com.example.quizapp.ui.presentation.favorite_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.components.CustomLoading

@Composable
fun FavoritePage(
    viewModel: FavoriteViewModel
) {
    val state = viewModel.favoriteWords.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        when(state.value){
            is FavoriteState.Loading -> {
                CustomLoading()
            }
            is FavoriteState.Success -> {
                if ((state.value as FavoriteState.Success).data.isEmpty()){
                    Text(modifier = Modifier.align(Alignment.Center), text = "No favorite words")
                } else {
                    Text(modifier = Modifier.align(Alignment.Center), text = "Favorite words")
                }
            }
            is FavoriteState.Error -> {
                Text(modifier = Modifier.align(Alignment.Center), text = "Error")
            }
        }
    }

}