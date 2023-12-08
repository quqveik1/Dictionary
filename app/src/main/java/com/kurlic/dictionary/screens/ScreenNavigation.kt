package com.kurlic.dictionary.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenTag) {
        composable(MainScreenTag) { MainScreen(navController) }
        composable(TypeScreenTag) { TypeScreen(navController) }
        composable(NewWordScreenTag) { NewWordScreen(navController) }
        composable(WordListScreenTag) { WordListScreen(navController) }
    }
}