package com.example.quizapp.ui.presentation.main_page.nav

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.PratikFagadiya.smoothanimationbottombar.properties.BottomBarProperties
import com.PratikFagadiya.smoothanimationbottombar.ui.SmoothAnimationBottomBar
import com.example.quizapp.R
import com.example.quizapp.core_utils.routes.AppRoutes
import com.example.quizapp.ui.presentation.favorite_page.FavoritePage
import com.example.quizapp.ui.presentation.home_page.HomePage
import com.example.quizapp.ui.presentation.search_page.SearchPage
import com.example.quizapp.ui.theme.Blue
import com.example.quizapp.ui.theme.BlueTint
import com.example.quizapp.ui.theme.Grey
import com.example.quizapp.ui.theme.GreyTint
import com.example.quizapp.ui.theme.Orange


@Composable
fun AppNavigation() {
    val bottomNavigationItems = listOf(
        SmoothAnimationBottomBarScreens(
            AppRoutes.HomePage.route,
            stringResource(id = R.string.home_page),
            R.drawable.ic_home_page
        ), SmoothAnimationBottomBarScreens(
            AppRoutes.SearchPage.route,
            stringResource(id = R.string.search_page),
            R.drawable.ic_search_page
        ), SmoothAnimationBottomBarScreens(
            AppRoutes.FavoritePage.route,
            stringResource(id = R.string.favorite_page),
            R.drawable.ic_favorite
        )
    )
    val navController = rememberNavController()
    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            SmoothAnimationBottomBar(navController,
                bottomNavigationItems,
                initialIndex = currentIndex,
                bottomBarProperties = BottomBarProperties(
                    backgroundColor = Color.Transparent,     // Change the background color
                    indicatorColor = Orange.copy(alpha = 0.3F),  // Change the indicator color with Alpha
                    iconTintColor = Color.Gray, // Change the icon tint color
                    iconTintActiveColor = Color.Black, // Change the active icon tint color
                    textActiveColor = Color.Black, // Change the active text color
                    cornerRadius = 18.dp,  // Increase the corner radius
//            fontFamily = JostFont,  // Change the font family
                    fontWeight = FontWeight.Medium,  // Change the font weight
                    fontSize = 16.sp // Increase or Decrease the font size
                ),
                onSelectItem = {
                    Log.i("SMOOTH_ANIMATION_BAR", "onCreate: Selected Item ${it.name}")
                })
        }
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        NavHost(navController, startDestination = AppRoutes.HomePage.route) {
            composable(AppRoutes.HomePage.route) {
                HomePage()
            }

            composable(AppRoutes.SearchPage.route) {
                SearchPage()
            }

            composable(AppRoutes.FavoritePage.route) {
                FavoritePage()
            }
        }
    }

}
