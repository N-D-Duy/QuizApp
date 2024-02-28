package com.example.quizapp.ui.presentation.home_page

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.ui.components.CustomLoading
import com.example.quizapp.ui.components.CustomSnackBar
import com.example.quizapp.ui.presentation.home_page.components.HomeComponents
import com.example.quizapp.ui.presentation.home_page.components.HomePager

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val mainState = viewModel.dataState.collectAsState()
    val downloadWordsState = viewModel.downloadWordsState.collectAsState()

    when (mainState.value) {
        is HomeState.Loading -> {
            // Show loading
            CustomLoading()
        }

        is HomeState.Success -> {
            // Show success
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                val data = (mainState.value as HomeState.Success).data
                if (data.isEmpty()
                    && downloadWordsState.value !is HomeViewModel.DownloadWords.Loading
                    && downloadWordsState.value !is HomeViewModel.DownloadWords.DownloadWordsError
                ) {
                    DownloadWords(context, viewModel, downloadWordsState.value)
                    viewModel.fetchRandomWord()
                } else {
                    HomeScreen(data = data, viewModel)
                }
            }
        }

        is HomeState.Error -> {
            // Show failed
            CustomSnackBar(
                message = (mainState.value as HomeState.Error).error,
                actionLabel = "Retry",
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.fetchRandomWord()
            }
        }
    }


}

@Composable
fun DownloadWords(
    context: Context,
    viewModel: HomeViewModel,
    state: HomeViewModel.DownloadWords
) {
    val isDownloadButtonClicked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Words Found, Please Download Words")
        Button(
            onClick = {
                isDownloadButtonClicked.value = true
                viewModel.downloadWords(context)
            }
        ) {
            Text(text = "Download Words")
        }
        if (state is HomeViewModel.DownloadWords.DownloadWordsError) {
            CustomSnackBar(
                message = state.message,
                actionLabel = "Retry",
                modifier = Modifier.weight(1f)
            ) {
                isDownloadButtonClicked.value = true
                viewModel.downloadWords(context)
            }

        }
    }

    // Hiển thị loading nếu đã nhấn nút tải xuống
    if (state is HomeViewModel.DownloadWords.Loading && isDownloadButtonClicked.value) {
        CustomLoading()
    }
}

@Composable
fun HomeScreen(
    data: List<WordInfo>,
    viewModel: HomeViewModel
) {
    HomePager(words = data, viewModel = viewModel)
}




