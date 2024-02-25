package com.example.quizapp.ui.presentation.onboading

import androidx.annotation.DrawableRes
import com.example.quizapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Welcome to QuizQuest!",
        description = "Dive into the world of trivia with QuizQuest! Test your knowledge and learn something new.",
        image = R.drawable.onboarding_1
    ),
    Page(
        title = "Get Quizzing Anywhere, Anytime!",
        description = "Take your love for quizzes offline with our app! No internet? No problem!",
        image = R.drawable.onboading_2
    ),
    Page(
        title = "Start Your Offline Quiz Adventure!",
        description = "Get started with QuizQuest and start your offline quiz adventure today!",
        image = R.drawable.onboarding_3
    )
)