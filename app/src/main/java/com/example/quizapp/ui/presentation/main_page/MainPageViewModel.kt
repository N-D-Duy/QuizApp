package com.example.quizapp.ui.presentation.main_page

import androidx.lifecycle.ViewModel
import com.example.quizapp.feartures.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCases: WordUseCases
): ViewModel() {
}