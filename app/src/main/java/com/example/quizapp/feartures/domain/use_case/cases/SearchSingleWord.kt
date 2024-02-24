package com.example.quizapp.feartures.domain.use_case.cases

import android.util.Log
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.quizapp.feartures.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchSingleWord(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<Resource<List<WordInfo>>> {
        if(query.isBlank()) {
            return flow {
                Log.e("GetSingleWordFromWordTable", "invoke: query is blank")
            }
        }
        return repository.getWordInfoLikeFromWordTable(query)
    }
}