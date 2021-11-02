package com.example.room_tutorial.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "All Users"){
        composable ("All Users"){
            AllUsers(navController = navController)
        }
        composable("Add User"){
            AddUser(navController)
        }
        composable(
            route = "Update User/{userId}",
            arguments = listOf(navArgument("userId"){
                type = NavType.IntType
            })
        ){ navBackStackEntry ->
            UpdateUser(navController = navController, userId = navBackStackEntry.arguments!!.getInt("userId") )
        }

    }
}







