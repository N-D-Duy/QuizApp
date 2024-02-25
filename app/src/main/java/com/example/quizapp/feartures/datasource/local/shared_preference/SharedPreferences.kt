package com.example.quizapp.feartures.datasource.local.shared_preference


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface MySharedPreferences {
    fun saveOnBoardingEntry()
    fun isOnBoardingEntry(): Boolean
}

class SharedPreferencesHelper (context: Context) : MySharedPreferences {

    companion object {
        private const val PREF_NAME = "MyAppPrefs"
        private const val PREF_ONBOARDING_COMPLETED = "OnBoardingCompleted"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun saveOnBoardingEntry() {
        sharedPreferences.edit().putBoolean(PREF_ONBOARDING_COMPLETED, true).apply()
    }

    override fun isOnBoardingEntry(): Boolean {
        return sharedPreferences.getBoolean(PREF_ONBOARDING_COMPLETED, false)
    }
}
