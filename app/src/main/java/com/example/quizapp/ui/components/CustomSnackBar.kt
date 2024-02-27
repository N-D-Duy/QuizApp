package com.example.quizapp.ui.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomSnackBar(
    message: String,
    actionLabel: String? = null,
    modifier: Modifier,
    onActionClick: (() -> Unit)? = null

) {
    Snackbar(
        modifier = modifier,
        action = {
            actionLabel?.let {
                SnackBarAction(onActionClick, actionLabel)
            }
        }
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SnackBarAction(onActionClick: (() -> Unit)? = null, actionLabel: String) {
    TextButton(onClick = onActionClick ?: {}) {
        Text(text = actionLabel)
    }
}
