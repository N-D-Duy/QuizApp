package com.example.quizapp.feartures.domain.repository

import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.enums.DismissDuration
import kotlinx.coroutines.flow.Flow

interface Repository {
    //for search
    fun getWordInfoLikeFromWordTable(query: String): Flow<Resource<List<WordInfo>>>

    suspend fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>>

    suspend fun insertWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>>

    suspend fun fetchRandomUnusedWordsFromWordTable(): Flow<Resource<List<WordInfo>>>
    suspend fun updateIsUsed(isUsed: Boolean, word: String): Flow<Resource<String>>
    suspend fun updateSkip(isSkipped: Boolean, word: String): Flow<Resource<String>>
    suspend fun updateDismissDuration(
        dismissDuration: DismissDuration,
        word: String
    ): Flow<Resource<String>>

    suspend fun downloadWordInfoFromApiAndInsertToWordTable(words: List<String>): Flow<Resource<List<WordInfo>>>
    suspend fun searchWordInfoFromApi(query: String): Flow<Resource<WordInfo>>
    suspend fun updateExpiredTime(expiredTime: Long, word: String): Flow<Resource<String>>
    suspend fun updateFavorite(isFavorite: Boolean, word: String): Flow<Resource<String>>
    suspend fun fetchFavoriteWordsFromWordTable(): Flow<Resource<List<WordInfo>>>

}