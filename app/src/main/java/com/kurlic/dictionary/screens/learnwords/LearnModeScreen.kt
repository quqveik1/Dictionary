package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kurlic.dictionary.elements.StyledDivider
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.elements.StyledTextField
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.TrainTypes
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel
import com.kurlic.dictionary.ui.theme.LightGray

const val LearnModeScreenTag = "MODE"

@Composable
fun LearnModeScreen(
    navController: NavController,
    trainViewModel: TrainViewModel,
    wordListViewModel: WordListViewModel
) {
    var trainWordsLen by rememberSaveable {
        mutableStateOf(5)
    }
    val trainWordsLenInput = rememberSaveable {
        mutableStateOf("$trainWordsLen")
    }
    LaunchedEffect(trainWordsLenInput.value) {
        if (trainWordsLenInput.value.isNotEmpty()) {
            trainWordsLen = trainWordsLenInput.value.toInt()
        } else {
            trainWordsLen = 0
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var isLearnByKeyChecked by rememberSaveable {
            mutableStateOf(true)
        }
        val options = listOf(
            R.string.flip_cards,
            R.string.writing
        )
        val selectedOption = rememberSaveable { mutableStateOf(options[0]) }

        Column {
            StyledText(text = stringResource(id = R.string.card_train_type))
            options.forEach { id ->
                Row {
                    RadioButton(
                        selected = id == selectedOption.value,
                        onClick = { selectedOption.value = id },
                        colors = RadioButtonDefaults.colors(selectedColor = LightGray)
                    )
                    StyledText(text = stringResource(id = id))
                }
            }
        }

        StyledDivider()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StyledCheckBox(checked = isLearnByKeyChecked,
                onCheckedChange = { isLearnByKeyChecked = it })
            StyledText(text = stringResource(id = R.string.study_by_keys))
        }

        StyledDivider()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StyledText(text = stringResource(id = R.string.word_count))
            StyledTextField(
                textState = trainWordsLenInput,
                label = stringResource(id = R.string.input),
                onlyNumbers = true
            )
        }
        StyledButton(text = stringResource(id = R.string.study),
            onClick = {
                var trainTypes = TrainTypes.Writing
                if (selectedOption.value == R.string.flip_cards) {
                    trainTypes = TrainTypes.Cards
                }
                goToLearnScreen(
                    wordListViewModel,
                    trainViewModel,
                    navController,
                    isLearnByKeyChecked,
                    trainTypes,
                    trainWordsLen
                )
            })
    }
}

private fun goToLearnScreen(
    wordListViewModel: WordListViewModel,
    trainViewModel: TrainViewModel,
    navController: NavController,
    learnByKey: Boolean,
    trainTypes: TrainTypes,
    trainWordsLen: Int
) {
    val trainData = buildWordsSet(
        wordListViewModel,
        learnByKey,
        trainTypes,
        trainWordsLen
    )
    navController.navigate(LearnWordsScreenTag) {
        trainViewModel.setTrainData(
            trainData
        )
    }
}

private fun buildWordsSet(
    wordListViewModel: WordListViewModel,
    learnByKey: Boolean,
    trainTypes: TrainTypes,
    trainWordsLen: Int
): TrainData {
    val learningWords = wordListViewModel.words.value!!.shuffled().take(trainWordsLen)

    return TrainData(
        words = learningWords,
        learnByKey = learnByKey,
        trainTypes = trainTypes
    )
}