package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledCard
import com.kurlic.dictionary.elements.StyledLinearProgressIndicator
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.TypeScreenTag
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel
import com.kurlic.dictionary.screens.learnwords.traindata.getGivenStringFromWord
import com.kurlic.dictionary.screens.learnwords.traindata.getLearnStringFromWord
import com.kurlic.dictionary.ui.theme.CorrectGreen
import com.kurlic.dictionary.ui.theme.ErrorRed

const val FinalScreenTag = "Cold"

@Composable
fun FinalScreen(
    navController: NavController,
    trainViewModel: TrainViewModel
) {
    val data by trainViewModel.trainData.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val progress = data!!.learnedWords.size.toFloat() / data!!.words.size.toFloat()
        StyledLinearProgressIndicator(
            progress = progress
        )

        StyledText(
            text = stringResource(id = R.string.you_have_learned) + "${data!!.learnedWords.size}/${data!!.words.size}" + stringResource(id = R.string.words),
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(data!!.words) { word ->
                val isWordLearned = data!!.learnedWords.contains(word)

                StyledCard(backgroundColor = if (isWordLearned) CorrectGreen else ErrorRed) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        StyledText(
                            text = getGivenStringFromWord(
                                word,
                                data!!.learnByKey
                            )
                        )
                        StyledText(
                            text = getLearnStringFromWord(
                                word,
                                data!!.learnByKey
                            )
                        )
                    }
                }
            }
        }

        StyledButton(text = stringResource(id = R.string.return_back),
            onClick = {
                navController.navigate(TypeScreenTag) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                }
            })
    }
}