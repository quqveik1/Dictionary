package com.kurlic.dictionary.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.kurlic.dictionary.screens.learnwords.FinalScreen
import com.kurlic.dictionary.screens.learnwords.FinalScreenTag
import com.kurlic.dictionary.screens.learnwords.LearnWordsScreenTag
import com.kurlic.dictionary.screens.learnwords.TestLearnWordsScreen
import com.kurlic.dictionary.screens.learnwords.TrainData

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreenTag
    ) {
        composable(MainScreenTag) { MainScreen(navController) }
        composable(TypeScreenTag) { TypeScreen(navController) }
        composable(NewWordScreenTag) { NewWordScreen(navController) }
        composable(WordListScreenTag) { WordListScreen(navController) }
        composable(LearnWordsScreenTag) { TestLearnWordsScreen(navController) }
        composable(
            "$FinalScreenTag/{trainDataJson}",
            arguments = listOf(navArgument("trainDataJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val gson = Gson()
            val trainDataJson = backStackEntry.arguments?.getString("trainDataJson")
            val trainData = gson.fromJson(
                trainDataJson,
                TrainData::class.java
            )

            FinalScreen(
                navController = navController,
                trainData = trainData
            )
        }

    }
}