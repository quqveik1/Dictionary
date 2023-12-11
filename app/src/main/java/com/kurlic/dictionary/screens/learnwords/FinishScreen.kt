package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.TypeScreenTag

const val FinalScreenTag = "COLDLEGS"

@Composable
fun FinalScreen(
    navController: NavController,
    trainData: TrainData
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyledText(text = "You've successfully learned ${trainData.learnedWords.size}/${trainData.words.size} words")
        StyledButton(
            text = "Come back",
            onClick = { navController.navigate(TypeScreenTag) })
    }
}