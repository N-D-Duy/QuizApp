package com.example.quizapp.feartures.domain.use_case.cases

import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.feartures.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class DownloadWordsFromApi(
    private val repository: Repository
) {
    suspend operator fun invoke(words: List<String>): Flow<Resource<List<WordInfo>>> {
        if(words.isEmpty()){
            throw IllegalArgumentException("Words can't be empty")
        }
        return repository.downloadWordInfoFromApiAndInsertToWordTable(words)
    }
}