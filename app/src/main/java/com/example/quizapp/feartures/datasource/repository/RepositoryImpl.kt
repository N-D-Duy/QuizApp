package com.example.quizapp.feartures.datasource.repository

import android.util.Log
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
    override fun searchWordInfo(query: String): Flow<Resource<List<WordInfo>>> {
        return flow {
            try {
                val result: ArrayList<WordInfo> = arrayListOf()

                //first, check all words that contain the query from the local database
                val localWords = db.wordDao.getWordInfoLike(query)
                if(localWords.isEmpty()){
                    //if no words found, fetch from the api, and insert to the local database
                    val response = api.getWordInfo(query)
                    if(response.isNotEmpty()){
                        val wordEntity = response.first().toWordInfoEntity()
                        db.wordDao.insertWord(wordEntity)
                        result.add(wordEntity.toWordInfo())
                    }
                } else {
                    result.addAll(localWords.map { it.toWordInfo() })
                }
                emit(Resource.Success(result))
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

    override fun fetchRandomUnusedWordsFromWordTable(): Flow<Resource<List<WordInfo>>> {
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
                Log.e("RepositoryImpl", "Start download: $words")
                val result: ArrayList<WordInfoEntity> = arrayListOf()
                //download word info from api and insert to word table
                for (word in words) {
                    val wordInfo = api.getWordInfo(word).map { it.toWordInfoEntity() }
                    //check if word is already in the table
                    val isWordExist = db.wordDao.getWordInfo(word)
                    if (isWordExist == null) {
                        result.add(wordInfo.first())
                    }
                }
                db.wordDao.insertWords(result.toList())
                for(i: Int in 0 until result.size) {
                    Log.e("RepositoryImpl", "Inserted: ${result[i].word}")
                }
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
                val result = db.wordDao.updateFavorite(isFavorite, word)
                emit(Resource.Success("Word updated successfully, $result"))
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

    override fun fetchFavoriteWordsFromWordTable(): Flow<Resource<List<WordInfo>>> {
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

    override fun isFavorite(word: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                //check if word is favorite
                val isFavorite = db.wordDao.isFavorite(word)
                emit(Resource.Success(isFavorite))
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