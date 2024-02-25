package com.example.quizapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.quizapp.ui.presentation.main_page.MainPage
import com.example.quizapp.ui.presentation.onboading.OnBoardingEvent
import com.example.quizapp.ui.presentation.onboading.OnBoardingScreen
import com.example.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            QuizAppTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        var showOnboarding by remember { mutableStateOf(!mainViewModel.isOnBoardingEntry()) }

        when {
            showOnboarding -> {
                OnBoardingScreen(onEvent = {
                    when (it) {
                        is OnBoardingEvent.SaveAppEntry -> {
                            showOnboarding = false
                            mainViewModel.saveOnBoardingEntry()
                        }
                    }
                })
            }
            else -> {
                MainPage()
            }
        }
    }
}


