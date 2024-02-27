package com.example.quizapp.feartures.domain.use_case.cases

import com.example.quizapp.feartures.domain.repository.Repository

class SearchWord (
    private val repository: Repository
){
    operator fun invoke(query: String) = repository.searchWordInfo(query)
}