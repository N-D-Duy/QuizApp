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
        return when(type){
            UpdateWordField.USED -> {
                // update used
                repository.updateIsUsed(data, word)
            }

            UpdateWordField.SKIP -> {
                // update skip
                repository.updateSkip(data, word)
            }

            UpdateWordField.FAVOURITE -> {
                // update favourite
                repository.updateFavorite(data, word)
            }
        }
    }
}