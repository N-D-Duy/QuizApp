package com.example.quizapp.ui.presentation.home_page.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.ui.presentation.home_page.HomeViewModel
import com.example.quizapp.ui.widgets.CustomLoading
import com.example.quizapp.ui.widgets.CustomSnackBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager(
    words: List<WordInfo>,
    viewModel: HomeViewModel,
    context: Context
) {
    val pagerState = rememberPagerState(
        pageCount = { words.size })
    val isLastPage = remember(pagerState.currentPage) {
        mutableStateOf(pagerState.currentPage == words.lastIndex)
    }
    val showDialog = remember { mutableStateOf(false) }
    val dialogActionCompleted = remember { mutableStateOf(false) }
    val isDownloadedClicked = remember { mutableStateOf(false) }
    //register downloadWordsState
    val result = viewModel.downloadWordsState.collectAsState()
    val isDownloaded = remember { mutableStateOf(false) }
    val scrollToFirstPage = remember { mutableStateOf(false) }

    Log.w("HomePager", "isLastPage: ${isLastPage.value}")
    Log.w("HomePager", "download start state: ${result.value}")

    LaunchedEffect(pagerState.currentPage) {
        isLastPage.value = pagerState.currentPage == words.lastIndex
    }


    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {
        if (isLastPage.value && !dialogActionCompleted.value) {
            showDialog.value = true
        }
        if(!isLastPage.value){
            showDialog.value = false
        }
        if (isDownloaded.value && isLastPage.value && dialogActionCompleted.value) {
            IconButton(onClick = {
                try {
                    val downloadState =
                        result.value as HomeViewModel.DownloadWords.DownloadWordsSuccess
                    isDownloaded.value = false
                    isDownloadedClicked.value = false
                    showDialog.value = false
                    dialogActionCompleted.value = false
                    // Scroll to first page
                    scrollToFirstPage.value = true
                    viewModel.resetDownloadState()
                    viewModel.updateListWords(downloadState.words)
                } catch (e: Exception) {
                    Log.e("HomePager", "Error: $e")
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            PageItem(
                word = words[it],
                viewModel = viewModel,
                context = context,
                currentList = words
            )
        }
    }

    // Hiển thị dialog khi showDialog là true
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false; dialogActionCompleted.value = true },
            title = { Text(text = "Want more?") },
            text = { Text(text = "Would you like to download more words? Click button below") },
            confirmButton = {
                Button(
                    onClick = {
                        // Thực hiện tải thêm từ khi người dùng chọn "Download"
                        viewModel.downloadWords(context)
                        dialogActionCompleted.value = true
                        showDialog.value = false
                        isDownloadedClicked.value = true
                    }
                ) {
                    Text("Download")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        dialogActionCompleted.value = true
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    LaunchedEffect(scrollToFirstPage.value) {
        if (scrollToFirstPage.value) {
            pagerState.animateScrollToPage(page = 0)
            scrollToFirstPage.value = false
        }
    }

    when (result.value) {
        is HomeViewModel.DownloadWords.Loading -> {
            if (isLastPage.value && isDownloadedClicked.value) {
                dialogActionCompleted.value = true
                CustomLoading()
                Log.w("HomePager", "Loading")
            }
        }

        is HomeViewModel.DownloadWords.DownloadWordsError -> {
            isDownloaded.value = false
            dialogActionCompleted.value = false
            CustomSnackBar(message = (result.value as HomeViewModel.DownloadWords.DownloadWordsError).message)
            Log.w("HomePager", "Error: ${(result.value as HomeViewModel.DownloadWords.DownloadWordsError).message}")
        }

        is HomeViewModel.DownloadWords.DownloadWordsSuccess -> {
            isDownloaded.value = true
            CustomSnackBar(message = "Download Success, refresh to got new words!")
            dialogActionCompleted.value = true
            Log.w("HomePager", "Success")
        }
    }
}


@Composable
fun PageItem(
    word: WordInfo,
    viewModel: HomeViewModel,
    context: Context,
    currentList: List<WordInfo>
) {
    HomeComponents(word = word, viewModel = viewModel, context = context, currentList = currentList)
}
