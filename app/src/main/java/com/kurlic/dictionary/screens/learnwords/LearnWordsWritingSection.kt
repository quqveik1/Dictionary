package com.kurlic.dictionary.screens.learnwords

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledCard
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.elements.StyledTextField
import com.kurlic.dictionary.ui.theme.CorrectGreen
import com.kurlic.dictionary.ui.theme.ErrorRed


@Composable
fun LearnWordWritingSection(
    word: WordEntity,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {
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

    val buttonColorGreen = ButtonDefaults.buttonColors(CorrectGreen)
    val buttonColorRed = ButtonDefaults.buttonColors(ErrorRed)

    LaunchedEffect(isUserCorrect.value) {
        if (isUserCorrect.value != null) {
            checkButtonText.value = nextStr
            checkButtonColor.value = if (isUserCorrect.value!!) buttonColorGreen else buttonColorRed
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyledText(
            text = if (isLearnByKey) word.key else word.wordValue,
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
        )

        if (isUserCorrect.value != null && !isUserCorrect.value!!) {
            StyledCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                backgroundColor = CorrectGreen
            ) {
                StyledText(text = if (isLearnByKey) word.wordValue else word.key)

            }
        }

        StyledTextField(
            textState = userAnswer,
            label = stringResource(id = if (isLearnByKey) R.string.input_german else R.string.input_russian),
            enabled = isUserCorrect.value == null
        )

        StyledButton(
            onClick = {
                if (isUserCorrect.value == null) {
                    val isCorrect = if (isLearnByKey) {
                        userAnswer.value == word.wordValue
                    } else {
                        userAnswer.value == word.key
                    }

                    onAnswerGiven(isCorrect)

                    isUserCorrect.value = isCorrect
                } else {
                    isUserCorrect.value = null
                    userAnswer.value = ""
                    checkButtonText.value = checkStr
                    checkButtonColor.value = null
                    onNextQuestion()
                }
            },
            text = checkButtonText.value,
            buttonColors = checkButtonColor.value
        )
    }
}