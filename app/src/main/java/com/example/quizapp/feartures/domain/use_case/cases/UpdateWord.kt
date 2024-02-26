package com.example.quizapp.feartures.domain.use_case.cases

import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.enums.UpdateWordField
import com.example.quizapp.feartures.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class UpdateWord(
    private val repository: Repository
){
    suspend operator fun invoke(word: String, data: Boolean, type: UpdateWordField): Flow<Resource<String>> {
        if(word.isEmpty() || word.isBlank()){
            throw IllegalArgumentException("Word cannot be empty")
        }
        when(type){
            UpdateWordField.USED -> {
                // update used
                return repository.updateSkip(data, word)
            }
            UpdateWordField.SKIP -> {
                // update skip
                 return repository.updateSkip(data, word)
            }
            UpdateWordField.FAVOURITE -> {
                // update favourite
                return repository.updateFavorite(data, word)
            }
        }
    }
}