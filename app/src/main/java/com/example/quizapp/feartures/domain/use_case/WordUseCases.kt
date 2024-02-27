package com.example.quizapp.feartures.domain.use_case

import com.example.quizapp.feartures.domain.use_case.cases.DownloadWordsFromApi
import com.example.quizapp.feartures.domain.use_case.cases.GetFavoriteWords
import com.example.quizapp.feartures.domain.use_case.cases.GetRandomUnusedWordsFromWordTable
import com.example.quizapp.feartures.domain.use_case.cases.SearchWord
import com.example.quizapp.feartures.domain.use_case.cases.UpdateWord

data class WordUseCases(
    val searchWord: SearchWord,
    val fetchRandomUnusedWordsFromWordTable: GetRandomUnusedWordsFromWordTable,
    val updateWord: UpdateWord,
    val getFavoriteWords: GetFavoriteWords,
    val downloadWordsFromApi: DownloadWordsFromApi
)
