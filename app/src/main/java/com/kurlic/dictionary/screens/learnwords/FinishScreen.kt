package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.TypeScreenTag
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel

const val FinalScreenTag = "Cold"

@Composable
fun FinalScreen(
    navController: NavController,
    trainViewModel: TrainViewModel
) {
    val trainData by trainViewModel.trainData.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (trainData != null) {
            StyledText(text = stringResource(id = R.string.you_have_learned) + " ${trainData!!.learnedWords.size}/${trainData!!.words.size} " + stringResource(id = R.string.words))
        }
        StyledButton(
            text = stringResource(id = R.string.return_back),
            onClick = {
                navController.navigate(TypeScreenTag) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                }
            })
    }
}