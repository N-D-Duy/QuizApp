package com.example.quizapp.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizapp.feartures.domain.model.WordInfo

@Composable
fun WordItem(word: WordInfo, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick).clip(shape = RoundedCornerShape(16.dp)).
            background(Color.Gray).border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Blue
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = word.word,
            modifier = Modifier.padding(16.dp)
        )
    }
}