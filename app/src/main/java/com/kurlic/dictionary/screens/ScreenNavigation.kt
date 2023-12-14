package com.kurlic.dictionary.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kurlic.dictionary.MainActivity
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.data.WordListViewModelFactory
import com.kurlic.dictionary.screens.learnwords.FinalScreen
import com.kurlic.dictionary.screens.learnwords.FinalScreenTag
import com.kurlic.dictionary.screens.learnwords.LearnModeScreen
import com.kurlic.dictionary.screens.learnwords.LearnModeScreenTag
import com.kurlic.dictionary.screens.learnwords.LearnWordsWriteScreen
import com.kurlic.dictionary.screens.learnwords.LearnWordsWriteScreenTag
import com.kurlic.dictionary.screens.learnwords.TestLearnWordsWriteScreen
import com.kurlic.dictionary.screens.learnwords.TestLearnWordsWriteScreenTag
import com.kurlic.dictionary.screens.learnwords.TrainViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val trainViewModel: TrainViewModel = viewModel()
    val wordListViewModel: WordListViewModel =
        viewModel(factory = WordListViewModelFactory(MainActivity.dao))
    NavHost(
        navController = navController,
        startDestination = MainScreenTag
    ) {
        composable(MainScreenTag) { MainScreen(navController) }
        composable(TypeScreenTag) { TypeScreen(navController) }
        composable(NewWordScreenTag) {
            NewWordScreen(
                navController,
                wordListViewModel
            )
        }
        composable(WordListScreenTag) {
            WordListScreen(
                navController,
                wordListViewModel
            )
        }
        composable(LearnModeScreenTag) {
            LearnModeScreen(
                navController,
                trainViewModel,
                wordListViewModel
            )
        }
        composable(LearnWordsWriteScreenTag) {
            LearnWordsWriteScreen(
                navController,
                trainViewModel
            )
        }
        composable(FinalScreenTag) {
            FinalScreen(
                navController = navController,
                trainViewModel = trainViewModel
            )
        }
        composable(TestLearnWordsWriteScreenTag) {
            TestLearnWordsWriteScreen(
                navController,
                trainViewModel
            )
        }

    }
}