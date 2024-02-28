package com.example.quizapp.ui.presentation.home_page.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.unit.dp
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.ui.presentation.home_page.HomeViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager(
    words: List<WordInfo>,
    viewModel: HomeViewModel
) {
    val pagerState = rememberPagerState(
        pageCount = { words.size })
    HorizontalPager(
        state = pagerState, modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            PageItem(word = words[it], viewModel = viewModel)
        }
    }
}

@Composable
fun PageItem(word: WordInfo, viewModel: HomeViewModel) {
    HomeComponents(word = word, viewModel = viewModel)
}
