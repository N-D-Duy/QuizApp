package com.example.quizapp.core_utils.routes

import com.example.quizapp.core_utils.enums.Routes


sealed class AppRoutes (
    val route: String
){
    data object WordDetailsPage: AppRoutes(Routes.WordDetailsPage.name)
    data object HomePage: AppRoutes(Routes.Home.name)
    data object SearchPage: AppRoutes(Routes.SearchPage.name)
    data object FavoritePage: AppRoutes(Routes.FavoritePage.name)
}