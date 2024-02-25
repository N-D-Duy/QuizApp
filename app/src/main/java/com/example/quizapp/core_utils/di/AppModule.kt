package com.example.quizapp.core_utils.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.quizapp.feartures.datasource.local.room.Converters
import com.example.quizapp.feartures.datasource.local.room.WordInfoDatabase
import com.example.quizapp.feartures.datasource.remote.DictionaryApi
import com.example.quizapp.feartures.datasource.repository.RepositoryImpl
import com.example.quizapp.feartures.domain.repository.Repository
import com.example.quizapp.feartures.domain.use_case.WordUseCases
import com.example.quizapp.feartures.domain.use_case.cases.GetRandomUnusedWordsFromWordTable
import com.example.quizapp.feartures.domain.use_case.cases.GetWordsLikeFromWordTable
import com.example.quizapp.feartures.domain.use_case.cases.SearchSingleWord
import com.example.quizapp.feartures.domain.use_case.cases.UpdateWord
import com.example.dictionaryapp.core_utils.parse.GsonParser
import com.example.quizapp.feartures.datasource.local.shared_preference.MySharedPreferences
import com.example.quizapp.feartures.datasource.local.shared_preference.SharedPreferencesHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: DictionaryApi,
        dao: WordInfoDatabase,
    ): Repository {
        return RepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideWordUseCase(repository: Repository): WordUseCases {
        return WordUseCases(
            getWordInfoLikeFromWordTable = GetWordsLikeFromWordTable(repository),
            getSingleWordInfoFromWordTable = SearchSingleWord(repository),
            fetchRandomUnusedWordsFromWordTable = GetRandomUnusedWordsFromWordTable(repository),
            updateWord = UpdateWord()
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): MySharedPreferences {
        return SharedPreferencesHelper(context)
    }



}