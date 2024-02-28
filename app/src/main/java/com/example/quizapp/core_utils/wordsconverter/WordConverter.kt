package com.example.quizapp.core_utils.wordsconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

class WordConverter(
    private val word: String
) {
    private var modifyWord = word;
    private var indices = mutableListOf<Int>()
    private lateinit var hiddenWord:
            AnnotatedString

    fun convertAndColorWord(): AnnotatedString {
        modifyWord = vowelToUnderscore()
        hiddenWord = colorUnderscores()
        return hiddenWord
    }

    // Convert word, like "book" -> "b__k", and return the modified word and the indices of replaced
    private fun vowelToUnderscore(): String {
        val pattern = "[aeiouAEIOU]".toRegex()
        val wordVowelToUnderscore = pattern.replace(word) {
            indices.add(it.range.first)
            "_"
        }

        // Handle words without vowels
        var finalModifiedWord = wordVowelToUnderscore
        if (indices.isEmpty()) {
            finalModifiedWord = if (modifyWord.length > 3) {
                val randomIndices = generateDistinctRandomIndices(modifyWord.length)
                val builder = StringBuilder(modifyWord)
                randomIndices.forEach { index ->
                    builder[index] = '_'
                    indices.add(index)
                }
                builder.toString()
            } else {
                val randomIndex = (modifyWord.indices).random()
                modifyWord.replaceRange(randomIndex, randomIndex + 1, "_")
            }
        }
        return finalModifiedWord
    }

    // Generate two distinct random indices
    private fun generateDistinctRandomIndices(length: Int): List<Int> {
        val index1 = (0 until length).random()
        var index2 = (0 until length).random()
        while (index2 == index1) {
            index2 = (0 until length).random()
        }
        return listOf(index1, index2)
    }


    // Color the characters in a word based on the indices
    private fun colorUnderscores(): AnnotatedString {
        return AnnotatedString.Builder().apply {
            modifyWord.forEachIndexed { index, char ->
                if (indices.contains(index)) {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(char.toString())
                    }
                } else {
                    append(char.toString())
                }
            }
        }.toAnnotatedString()
    }

    fun revealHiddenChars(): AnnotatedString {
        hiddenWord = convertAndColorWord()
        return AnnotatedString.Builder().apply {
            hiddenWord.forEachIndexed { index, charStyle ->
                if (indices.contains(index)) {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(word[index].toString())
                    }
                } else {
                    append(charStyle)
                }
            }
        }.toAnnotatedString()
    }
}




