package com.example.quizapp.feartures.domain.use_case.cases

import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.feartures.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomUnusedWordsFromWordTable (
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<List<WordInfo>>> {
        return repository.fetchRandomUnusedWordsFromWordTable()
    }
}