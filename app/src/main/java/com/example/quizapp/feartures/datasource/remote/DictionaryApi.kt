package com.example.quizapp.feartures.datasource.remote

import com.example.quizapp.feartures.datasource.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/" //api url
    }

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>
}