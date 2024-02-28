package com.example.quizapp.ui.presentation.home_page.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.core_utils.enums.UpdateWordField
import com.example.quizapp.core_utils.wordsconverter.WordConverter
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.ui.presentation.home_page.HomeViewModel

@Composable
fun HomeComponents(
    word: WordInfo,
    viewModel: HomeViewModel
) {
    val isPlaying = remember { mutableStateOf(false) }
    val isMenuShow = remember { mutableStateOf(false) }
    viewModel.isFavoriteWord(word.word)
    val isFavoriteWord = viewModel.isFavorite.collectAsState()

    val displayWordPair =
        remember { mutableStateOf(Pair(WordConverter(word.word).convertAndColorWord(), false)) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Button favorite
            when(isFavoriteWord.value){
                true -> {
                    IconButton(
                        onClick = {
                            //update favorite
                            viewModel.updateWord(
                                word.word,
                                !isFavoriteWord.value,
                                type = UpdateWordField.FAVOURITE
                            )
                        },
                        modifier = Modifier
                            .size(50.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite_filled),
                                contentDescription = null,
                                tint = Color.Yellow,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    )
                }
                false -> {
                    IconButton(
                        onClick = {
                            //update favorite
                            viewModel.updateWord(
                                word.word,
                                !isFavoriteWord.value,
                                type = UpdateWordField.FAVOURITE
                            )
                        },
                        modifier = Modifier
                            .size(50.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    )
                }
            }

            //Button option menu
            IconButton(
                onClick = {
                    isMenuShow.value = !isMenuShow.value
                },
                modifier = Modifier
                    .size(50.dp),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_filled),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    if (isMenuShow.value) {
                        //show menu
                        OptionDialog(onDismiss = {
                            isMenuShow.value = !isMenuShow.value
                        }, onItemSelected = {
                            //handle item selected (update database,..)
                            //not available yet

                        })
                    }
                }
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        //Word
        Text(
            text = displayWordPair.value.first,
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = {
                    displayWordPair.value = Pair(
                        if (displayWordPair.value.second) {
                            WordConverter(word.word).convertAndColorWord()
                        } else {
                            WordConverter(word.word).revealHiddenChars()
                        },
                        !displayWordPair.value.second
                    )
                }),
            style = TextStyle(
                fontSize = if (word.word.length > 10) 40.sp else 64.sp,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.size(16.dp))

        //Phonetic
        Text(
            text = word.phonetic ?: "/_/",
            modifier = Modifier
                .padding(16.dp),
            color = Color.Gray,
            style = TextStyle(
                fontSize = 36.sp
            )
        )

        //Button play sound
        IconButton(onClick = {
            isPlaying.value = !isPlaying.value
        }) {
            val icon = if (isPlaying.value) {
                R.drawable.ic_pause_filled
            } else {
                R.drawable.ic_play_filled
            }
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        //Meaning
        if (displayWordPair.value.second) {
            ScrollableColumn(
                text = word.meanings.joinToString("\n") { meaning ->
                    meaning.definitions.joinToString("\n") { definition ->
                        definition.definition ?: ""
                    }
                }
            )
        } else {
            Text(
                text = "Tap word to reveal meaning",
                style = TextStyle(fontSize = 18.sp),
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun ScrollableColumn(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(
            text = text,
            modifier = Modifier.verticalScroll(ScrollState(0)),
            style = TextStyle(
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}