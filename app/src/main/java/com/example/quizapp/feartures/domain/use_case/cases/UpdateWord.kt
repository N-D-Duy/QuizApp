package com.example.quizapp.feartures.domain.use_case.cases

import com.example.quizapp.feartures.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

class UpdateWord(
) {
     companion object{
         private lateinit var repository: Repository
         suspend fun updateSkip(isSkipped: Boolean, word: String): Flow<Resource<String>> {
             if(word.isBlank()) {
                 throw Exception("Word is blank")
             } else {
                 return repository.updateSkip(isSkipped, word)
             }
         }

         suspend fun updateFavorite(isFavorite: Boolean, word: String): Flow<Resource<String>> {
             if(word.isBlank()) {
                 throw Exception("Word is blank")
             } else {
                 return repository.updateFavorite(isFavorite, word)
             }
         }

         suspend fun updateIsUsed(isUsed: Boolean, word: String): Flow<Resource<String>> {
             if(word.isBlank()) {
                 throw Exception("Word is blank")
             } else {
                 return repository.updateIsUsed(isUsed, word)
             }
         }


     }
}