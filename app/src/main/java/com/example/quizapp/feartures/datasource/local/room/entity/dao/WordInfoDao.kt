package com.example.quizapp.feartures.datasource.local.room.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity
import com.example.quizapp.core_utils.enums.DismissDuration

@Dao
interface WordInfoDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordInfoEntity)

    //get word like query, used for search
    @Query("SELECT * FROM `word-table` WHERE word LIKE '%' || :word || '%'")
    fun getWordInfoLike(word: String): List<WordInfoEntity>

    @Query("SELECT * FROM `word-table` WHERE isUsed = 0 ORDER BY RANDOM() LIMIT 20")
    suspend fun fetchRandomUnusedWords(): List<WordInfoEntity>

    //update isUsed
    @Query("Update `word-table` Set isUsed= :isUsed Where word = :word")
    suspend fun updateIsUsed(isUsed: Boolean, word: String): Int
    //update isSkipped
    @Query("Update `word-table` Set isSkipped= :isSkipped Where word = :word")
    suspend fun updateSkip(isSkipped: Boolean, word: String): Int

    //update dismissDuration
    @Query("Update `word-table` Set dismissDuration= :dismissDuration Where word = :word")
    suspend fun updateDismissDuration(dismissDuration: DismissDuration, word: String): Int
    @Query("Update `word-table` Set expiredTime= :expiredTime Where word = :word")
    suspend fun updateExpiredTime(expiredTime: Long, word: String): Int

    @Query("Update `word-table` Set isFavorite= :isFavorite Where word = :word")
    suspend fun updateFavorite(isFavorite: Boolean, word: String): Int
    


}