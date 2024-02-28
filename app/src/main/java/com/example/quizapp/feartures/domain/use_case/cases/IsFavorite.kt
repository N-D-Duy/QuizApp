package com.example.quizapp.feartures.domain.use_case.cases

import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.feartures.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class IsFavorite(
    private val repository: Repository
){
    suspend operator fun invoke(word: String): Flow<Resource<Boolean>> {
        if(word.isEmpty()){
            throw IllegalArgumentException("Word can't be empty")
        }
        return repository.isFavorite(word)
    }
}