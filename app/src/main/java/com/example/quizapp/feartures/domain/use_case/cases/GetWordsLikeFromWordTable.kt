package com.example.quizapp.feartures.domain.use_case.cases

import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.feartures.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordsLikeFromWordTable (
    private val repository: Repository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<WordInfo>>> {
        if (query.isBlank()) {
            return flow {
                //return nothing if word got from repo is blank
            }
        }
        return repository.getWordInfoLikeFromWordTable(query)
    }
}