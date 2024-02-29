package com.example.quizapp.ui.presentation.word_details_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.quizapp.feartures.domain.model.Definition

@Composable
fun DefinitionItem(definition: Definition) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = definition.definition ?: "",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        val example = definition.example
        if (!example.isNullOrBlank()) {
            val annotatedExample = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Magenta)) {
                    append("Example: ")
                }
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(example)
                }
            }
            Text(
                text = annotatedExample
            )
        }
    }
}