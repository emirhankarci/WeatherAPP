package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.AboutScreen
import com.example.weatherapp.screens.FavoritesScreen
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.screens.MainViewModel
import com.example.weatherapp.screens.SearchScreen
import com.example.weatherapp.screens.SettingsScreen
import com.example.weatherapp.screens.WeatherSplashScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                   type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController, mainViewModel, city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(WeatherScreens.FavoritesScreen.name) {
            FavoritesScreen(navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }


    }
}