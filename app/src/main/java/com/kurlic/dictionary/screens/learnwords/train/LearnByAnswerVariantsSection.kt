package com.kurlic.dictionary.screens.learnwords.train

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R
import com.kurlic.dictionary.common.AfterAnswerGivenWaitingLen
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.getLearnStringFromWord
import kotlinx.coroutines.delay


@Composable
fun LearnByAnswerVariantsSection(
    word: WordEntity,
    trainData: TrainData,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {
    key(word) {
        val isVertical =
            LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        if (isVertical) {
            Column(modifier = Modifier.fillMaxSize()) {
                ScreenContent(
                    commonWeightModifier = Modifier.weight(1f),
                    word = word,
                    trainData = trainData,
                    onAnswerGiven = onAnswerGiven,
                    onNextQuestion = onNextQuestion,
                    isLearnByKey = isLearnByKey
                )
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                ScreenContent(
                    commonWeightModifier = Modifier.weight(1f),
                    word = word,
                    trainData = trainData,
                    onAnswerGiven = onAnswerGiven,
                    onNextQuestion = onNextQuestion,
                    isLearnByKey = isLearnByKey
                )
            }
        }
    }
}

@Composable
fun ScreenContent(
    commonWeightModifier: Modifier,
    word: WordEntity,
    trainData: TrainData,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {

    Column(
        modifier = commonWeightModifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StyledText(
            text = if (isLearnByKey) word.key else word.wordValue,
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
        )
    }

    val generationAns = rememberSaveable() {
        mutableStateOf(
            generateAnswerList(
                trainData,
                word
            )
        )
    }

    val answerSuggestList = rememberSaveable() {
        mutableStateOf(generationAns.value.first)
    }

    val correctAnswerIndex = rememberSaveable() {
        mutableStateOf(generationAns.value.second)
    }

    val buttons = answerSuggestList.value.chunked(2)

    var answerVariant by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    LaunchedEffect(answerVariant) {
        answerVariant?.let {
            delay(AfterAnswerGivenWaitingLen)
            onNextQuestion()
        }
    }

    Box(
        modifier = commonWeightModifier
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
                        var buttonColors: ButtonColors? = null
                        if (answerVariant != null && answerVariant == buttonIndex) {
                            if (buttonIndex == correctAnswerIndex.value) {
                                buttonColors =
                                    ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.correct_green))
                            } else {
                                buttonColors =
                                    ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.error_red))
                            }
                        }
                        if (answerVariant != null && buttonIndex == correctAnswerIndex.value) {
                            buttonColors =
                                ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.correct_green))
                        }
                        StyledButton(
                            text = answer,
                            onClick = {
                                if (answerVariant != null) return@StyledButton
                                val isCorrect = answer == getLearnStringFromWord(
                                    word,
                                    isLearnByKey
                                )
                                onAnswerGiven(isCorrect)
                                answerVariant = buttonIndex
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

fun generateAnswerList(
    trainData: TrainData,
    word: WordEntity
): Pair<List<String>, Int> {
    val correctAnswer = getLearnStringFromWord(
        word,
        trainData.learnByKey
    )
    val answer = listOf(correctAnswer)

    val randomAnswers = trainData.words.filter { it != word }.shuffled().take(3)
    val stringRandomAnswers = randomAnswers.map {
        getLearnStringFromWord(
            it,
            trainData.learnByKey
        )
    }

    val mixedAnswers = (answer + stringRandomAnswers).shuffled()
    val correctAnswerIndex = mixedAnswers.indexOf(correctAnswer)

    return Pair(
        mixedAnswers,
        correctAnswerIndex
    )
}
