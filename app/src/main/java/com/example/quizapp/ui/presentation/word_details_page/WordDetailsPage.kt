package com.example.quizapp.ui.presentation.word_details_page

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp.feartures.domain.model.WordInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDetailsPage(word: WordInfo, navigateUp: () -> Unit) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current

    DisposableEffect(Unit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateUp()
            }
        }
        backDispatcher?.onBackPressedDispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }


    //content
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = word.word,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navigateUp()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp).padding(bottom = 64.dp, top = 8.dp)
        ) {
            item {
                Text(
                    text = word.phonetic ?: "",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                )

                Text(
                    text = word.origin ?: "",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(word.meanings.size) { index ->
                val meaning = word.meanings[index]
                MeaningItem(meaning = meaning)
            }
        }
    }
}