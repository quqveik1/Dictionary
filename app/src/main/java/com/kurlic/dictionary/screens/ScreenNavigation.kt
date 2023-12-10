package com.kurlic.dictionary.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kurlic.dictionary.screens.learnwords.LearnWordsScreenTag
import com.kurlic.dictionary.screens.learnwords.TestLearnWordsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenTag) {
        composable(MainScreenTag) { MainScreen(navController) }
        composable(TypeScreenTag) { TypeScreen(navController) }
        composable(NewWordScreenTag) { NewWordScreen(navController) }
        composable(WordListScreenTag) { WordListScreen(navController) }
        composable(LearnWordsScreenTag) { TestLearnWordsScreen(navController) }
    }
}