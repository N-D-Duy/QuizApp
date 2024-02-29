package com.example.quizapp.ui.presentation.home_page

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.quizapp.ui.presentation.home_page.components.HomePager
import com.example.quizapp.ui.widgets.CustomLoading
import com.example.quizapp.ui.widgets.CustomSnackBar

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val mainState = viewModel.dataState.collectAsState()
    val downloadWordsState = viewModel.downloadWordsState.collectAsState()
    viewModel.resetDownloadState()

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
                ) {
                    DownloadWords(context, viewModel, downloadWordsState.value)
                    viewModel.fetchRandomWord()
                } else {
                    HomeScreen(data = data, viewModel, context)
                }
            }
        }

        is HomeState.Error -> {
            // Show failed
            Log.w("HomePage", "home state error")
            CustomSnackBar(message = (mainState.value as HomeState.Error).error)
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
            CustomSnackBar(message = state.message)
            Log.w("HomePages", "Error: ${state.message}")
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
    viewModel: HomeViewModel,
    context: Context
) {

    HomePager(words = data, viewModel = viewModel, context = context)
}




