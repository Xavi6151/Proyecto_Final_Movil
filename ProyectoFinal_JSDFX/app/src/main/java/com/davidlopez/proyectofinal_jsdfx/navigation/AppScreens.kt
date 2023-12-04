package com.davidlopez.proyectofinal_jsdfx.navigation

sealed class AppScreens(val route:String) {
    object MainScreen: AppScreens("MainScreen")
    object AddScreen: AppScreens("AddScreen")
    object EditScreen: AppScreens("EditScreen")
}