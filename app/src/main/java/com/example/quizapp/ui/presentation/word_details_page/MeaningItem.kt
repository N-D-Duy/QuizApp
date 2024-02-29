package com.example.quizapp.ui.presentation.word_details_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.feartures.domain.model.Definition
import com.example.quizapp.feartures.domain.model.Meaning

@Composable
fun MeaningItem(meaning: Meaning) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = meaning.partOfSpeech ?: "Unknown",
            color = Color.Red,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        for(definition in meaning.definitions) {
            DefinitionItem(definition = definition)
        }
    }
}
