package com.kurlic.dictionary.screens.learnwords.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.getLearnStringFromWord
import com.kurlic.dictionary.ui.theme.CorrectGreen
import com.kurlic.dictionary.ui.theme.ErrorRed

@Composable
fun LearnByAnswerVariantsSection(
    word: WordEntity,
    trainData: TrainData,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            StyledText(
                text = if (isLearnByKey) word.key else word.wordValue,
                fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
            )
        }

        val answerSuggestList = rememberSaveable() {
            mutableStateOf(
                generateAnswerList(
                    trainData,
                    word
                )
            )
        }

        val buttonColorsList = rememberSaveable {
            mutableStateOf(List(4) { null as Color? })
        }

        val buttons = answerSuggestList.value.chunked(2)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_standard))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                buttons.forEachIndexed { rowIndex, pair ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        pair.forEachIndexed { columnIndex, answer ->
                            val buttonIndex = rowIndex * 2 + columnIndex
                            val color = buttonColorsList.value[buttonIndex]
                            val buttonColors =
                                if (color != null) ButtonDefaults.buttonColors(containerColor = color) else null
                            StyledButton(
                                text = answer,
                                onClick = {
                                    val isCorrect = answer == getLearnStringFromWord(
                                        word,
                                        isLearnByKey
                                    )
                                    onAnswerGiven(isCorrect)
                                    if (isCorrect) {
                                        buttonColorsList.value = buttonColorsList.value.mapIndexed { index, _ ->
                                            if (index == buttonIndex) CorrectGreen else null
                                        }
                                    } else {
                                        buttonColorsList.value = buttonColorsList.value.mapIndexed { index, _ ->
                                            if (index == buttonIndex) ErrorRed else null
                                        }
                                    }
                                },
                                buttonColors = buttonColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

fun generateAnswerList(
    trainData: TrainData,
    word: WordEntity
): List<String> {
    val answer = listOf(
        getLearnStringFromWord(
            word,
            trainData.learnByKey
        )
    )

    val randomAnswers = trainData.words.filter { it != word }.shuffled().take(3)

    val stringRandomAnswers = randomAnswers.map {
        getLearnStringFromWord(
            it,
            trainData.learnByKey
        )
    }

    return (answer + stringRandomAnswers).shuffled()
}

