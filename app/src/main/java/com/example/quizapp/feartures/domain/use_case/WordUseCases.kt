package com.example.quizapp.feartures.domain.use_case

import com.example.quizapp.feartures.domain.use_case.cases.SearchSingleWord
import com.example.quizapp.feartures.domain.use_case.cases.GetWordsLikeFromWordTable
import com.example.quizapp.feartures.domain.use_case.cases.GetRandomUnusedWordsFromWordTable
import com.example.quizapp.feartures.domain.use_case.cases.UpdateWord

data class WordUseCases(
    val getWordInfoLikeFromWordTable: GetWordsLikeFromWordTable,
    val getSingleWordInfoFromWordTable: SearchSingleWord,
    val fetchRandomUnusedWordsFromWordTable: GetRandomUnusedWordsFromWordTable,
    val updateWord: UpdateWord
)
