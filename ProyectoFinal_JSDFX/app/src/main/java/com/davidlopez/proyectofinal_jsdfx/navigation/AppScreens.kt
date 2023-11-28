package com.davidlopez.proyectofinal_jsdfx.navigation

sealed class AppScreens(val route:String) {
    object MainScreen: AppScreens("MainScreen")
    object SearchScreen: AppScreens("SearchScreen")
    object AddScreen: AppScreens("AddScreen")
    object EditScreen: AppScreens("EditScreen")
}