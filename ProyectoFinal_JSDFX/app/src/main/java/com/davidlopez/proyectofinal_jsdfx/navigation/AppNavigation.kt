package com.davidlopez.proyectofinal_jsdfx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidlopez.proyectofinal_jsdfx.App
import com.davidlopez.proyectofinal_jsdfx.AppAgregarNotaTarea
import com.davidlopez.proyectofinal_jsdfx.AppBuscar

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route
    ){
        composable(route =AppScreens.MainScreen.route){
            App(modifier = Modifier,navController)
        }
        composable(route =AppScreens.SearchScreen.route){
            AppBuscar(modifier = Modifier,navController)
        }
        composable(route =AppScreens.AddScreen.route){
            AppAgregarNotaTarea(modifier = Modifier,navController)
        }
    }
}