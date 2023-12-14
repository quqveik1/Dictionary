package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledCheckBox

const val LearnModeScreenTag = "MODE"

@Composable
fun LearnModeScreen(
    navController: NavController,
    trainViewModel: TrainViewModel,
    wordListViewModel: WordListViewModel
) {
    val trainWordsLen = 5
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var isChecked by rememberSaveable {
            mutableStateOf(true)
        }

        StyledButton(text = stringResource(id = R.string.study_with_writing),
            onClick = {
                navController.navigate(LearnWordsWriteScreenTag) {
                    val learningWords =
                        wordListViewModel.words.value!!.shuffled().take(trainWordsLen)
                    trainViewModel.setTrainData(
                        TrainData(
                            words = learningWords,
                            learnByKey = isChecked
                        )
                    )
                    popUpTo(LearnModeScreenTag) { inclusive = true }
                }
            })
        StyledButton(text = stringResource(id = R.string.study_with_cards),
            onClick = { /*TODO*/ })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StyledCheckBox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(text = stringResource(id = R.string.study_by_keys))
        }
    }
}