package com.example.quizapp.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.quizapp.feartures.datasource.local.shared_preference.MySharedPreferences
import com.example.quizapp.feartures.datasource.local.shared_preference.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: MySharedPreferences
) : ViewModel(){
    fun saveOnBoardingEntry() {
        sharedPreferences.saveOnBoardingEntry()
    }
    fun isOnBoardingEntry(): Boolean {
        return sharedPreferences.isOnBoardingEntry()
    }
}