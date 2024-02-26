package com.example.quizapp.feartures.datasource.repository

import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity
import com.example.quizapp.feartures.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import com.example.quizapp.core_utils.enums.DismissDuration
import com.example.quizapp.feartures.datasource.local.room.WordInfoDatabase
import com.example.quizapp.feartures.datasource.remote.DictionaryApi
import com.example.quizapp.feartures.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl(
    private val api: DictionaryApi,
    private val db: WordInfoDatabase
) : Repository {
    override fun getWordInfoLikeFromWordTable(query: String): Flow<Resource<List<WordInfo>>> {
        return flow {
            try {
                //get word like query, used for search
                val wordInfoList = db.wordDao.getWordInfoLike(query).map { it.toWordInfo() }
                emit(Resource.Success(wordInfoList))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }


    override suspend fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>> {
        return flow {
            try {
                //insert single word to word table
                db.wordDao.insertWord(word)
                emit(Resource.Success("Word inserted successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun insertWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRandomUnusedWordsFromWordTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            try {
                //fetch random unused words from word table
                val wordInfoList = db.wordDao.fetchRandomUnusedWords().map { it.toWordInfo() }
                emit(Resource.Success(wordInfoList))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun updateIsUsed(isUsed: Boolean, word: String): Flow<Resource<String>> {
        return flow {
            try {
                //update isUsed
                db.wordDao.updateIsUsed(isUsed, word)
                emit(Resource.Success("Word updated successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun updateSkip(isSkipped: Boolean, word: String): Flow<Resource<String>> {
        return flow {
            try {
                //update isSkipped
                db.wordDao.updateSkip(isSkipped, word)
                emit(Resource.Success("Word updated successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun updateDismissDuration(
        dismissDuration: DismissDuration,
        word: String
    ): Flow<Resource<String>> {
        return flow {
            try {
                //update dismissDuration
                db.wordDao.updateDismissDuration(dismissDuration, word)
                emit(Resource.Success("Word updated successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun downloadWordInfoFromApiAndInsertToWordTable(words: List<String>): Flow<Resource<List<WordInfo>>> {
        return flow {
            try {
                var result: ArrayList<WordInfoEntity> = arrayListOf()
                //download word info from api and insert to word table
                for (word in words) {
                    val wordInfo = api.getWordInfo(word).map { it.toWordInfoEntity() }
                    result.add(wordInfo[0])
                }
                db.wordDao.insertWords(result.toList())
                emit(Resource.Success(result.map { it.toWordInfo() }))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun searchWordInfoFromApi(query: String): Flow<Resource<WordInfo>> {
        return flow {
            try {
                //search word info from api
                val wordInfoList = api.getWordInfo(query).map { it.toWordInfoEntity() }
                emit(Resource.Success(wordInfoList.first().toWordInfo()))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun updateExpiredTime(
        expiredTime: Long,
        word: String
    ): Flow<Resource<String>> {
        return flow {
            try {
                //update expiredTime
                db.wordDao.updateExpiredTime(expiredTime, word)
                emit(Resource.Success("Word updated successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun updateFavorite(isFavorite: Boolean, word: String): Flow<Resource<String>> {
        return flow {
            try {
                //update isFavorite
                db.wordDao.updateFavorite(isFavorite, word)
                emit(Resource.Success("Word updated successfully"))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    override suspend fun fetchFavoriteWordsFromWordTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            try {
                //fetch favorite words from word table
                val wordInfoList = db.wordDao.fetchFavoriteWords().map { it.toWordInfo() }
                emit(Resource.Success(wordInfoList))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }


}