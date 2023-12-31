package com.kurlic.dictionary.screens.learnwords.train

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.styled.StyledButton
import com.kurlic.dictionary.elements.styled.StyledCard
import com.kurlic.dictionary.elements.styled.StyledText
import com.kurlic.dictionary.elements.styled.StyledTextField
import com.kurlic.dictionary.screens.learnwords.isAnswerSame
import com.kurlic.dictionary.screens.learnwords.traindata.getGivenStringFromWord
import com.kurlic.dictionary.screens.learnwords.traindata.getLearnStringFromWord


@Composable
fun LearnByWritingSection(
    word: WordEntity,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {
    key(word) {
        val userAnswer = rememberSaveable {
            mutableStateOf("")
        }

        val isUserCorrect = rememberSaveable {
            mutableStateOf<Boolean?>(null)
        }

        val checkStr = stringResource(id = R.string.check)
        val nextStr = stringResource(id = R.string.next)

        val checkButtonText = rememberSaveable {
            mutableStateOf(checkStr)
        }
        val checkButtonColor = remember {
            mutableStateOf<ButtonColors?>(null)
        }

        val buttonColorGreen = ButtonDefaults.buttonColors(
            colorResource(id = R.color.correct_green)
        )
        val buttonColorRed = ButtonDefaults.buttonColors(colorResource(id = R.color.error_red))

        LaunchedEffect(isUserCorrect.value) {
            if (isUserCorrect.value != null) {
                checkButtonText.value = nextStr
                checkButtonColor.value =
                    if (isUserCorrect.value!!) buttonColorGreen else buttonColorRed
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StyledText(
                text = getGivenStringFromWord(
                    word,
                    isLearnByKey
                ),
                fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
            )

            if (isUserCorrect.value != null && !isUserCorrect.value!!) {
                StyledCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    backgroundColor = colorResource(id = R.color.correct_green)
                ) {
                    StyledText(
                        text = getLearnStringFromWord(
                            word,
                            isLearnByKey
                        )
                    )

                }
            }

            StyledTextField(
                textState = userAnswer,
                label = stringResource(id = if (isLearnByKey) R.string.input_key else R.string.input_value),
                enabled = isUserCorrect.value == null,
                modifier = Modifier.fillMaxWidth()
            )

            StyledButton(
                onClick = {
                    if (isUserCorrect.value == null) {
                        val isCorrect = isAnswerSame(
                            userAnswer.value,
                            getLearnStringFromWord(
                                word,
                                isLearnByKey
                            )
                        )

                        onAnswerGiven(isCorrect)

                        isUserCorrect.value = isCorrect
                    } else {
                        onNextQuestion()
                    }
                },
                text = checkButtonText.value,
                buttonColors = checkButtonColor.value
            )
        }
    }
}