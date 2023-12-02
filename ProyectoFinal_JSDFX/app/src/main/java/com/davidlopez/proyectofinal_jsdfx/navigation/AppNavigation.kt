package com.davidlopez.proyectofinal_jsdfx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidlopez.proyectofinal_jsdfx.App
import com.davidlopez.proyectofinal_jsdfx.AppAgregarNotaTarea
import com.davidlopez.proyectofinal_jsdfx.AppBuscar
import com.davidlopez.proyectofinal_jsdfx.AppEditarNotaTarea
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route,
    ){
        var nota = NotaEntity(0,"","","")
        composable(route =AppScreens.MainScreen.route){
            App(modifier = Modifier,navController,
                navigateToItemUpdate = {navController.navigate(AppScreens.EditScreen.route)
                nota=it})
        }
        composable(route =AppScreens.SearchScreen.route){
            AppBuscar(modifier = Modifier,navController,
                navigateToItemUpdate = {navController.navigate(AppScreens.SearchScreen.route)
                    nota=it})
        }
        composable(route =AppScreens.AddScreen.route){
            AppAgregarNotaTarea(modifier = Modifier,navController)
        }
        composable(route =AppScreens.EditScreen.route){
            AppEditarNotaTarea(modifier = Modifier,navController,nota)
        }
    }
}