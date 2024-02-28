package com.example.quizapp.ui.presentation.home_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quizapp.core_utils.enums.DismissDuration


@Composable
fun OptionDialog(
    onDismiss: () -> Unit,
    onItemSelected: (DismissDuration) -> Unit = {},
) {
    val items = DismissDuration.entries.toList()

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .size(300.dp, 300.dp)
        ) {
            Column {
                Text(
                    text = "Select Duration You Want To Skip This Word",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items.size) { index ->
                        val item = items[index]
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .clickable {
                                    onItemSelected(item)
                                    onDismiss()
                                }
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

