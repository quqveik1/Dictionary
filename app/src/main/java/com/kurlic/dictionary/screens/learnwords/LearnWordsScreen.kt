package com.kurlic.dictionary.screens.learnwords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordCategory
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledTextField

const val LearnWordsScreenTag = "LEARN"

@Composable
fun TestLearnWordsScreen(navController: NavController) {
    val words = listOf(
        WordEntity(
            id = 1,
            key = "дом",
            keyLang = LangName.Russian,
            wordValue = "Haus",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        ),
        WordEntity(
            id = 2,
            key = "кошка",
            keyLang = LangName.Russian,
            wordValue = "Katze",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        ),
        WordEntity(
            id = 3,
            key = "солнце",
            keyLang = LangName.Russian,
            wordValue = "Sonne",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        )
    )
    LearnWordsScreen(
        navController = navController,
        words = words
    )
}

@Composable
fun LearnWordsScreen(
    navController: NavController,
    words: List<WordEntity>
) {
    var currentIndex by remember {
        mutableStateOf(0)
    }
    val currentWord = words.getOrNull(currentIndex)

    val onAnswerGiven: (isCorrect: Boolean) -> Unit = {
    }
    val onNextQuestion: () -> Unit = {
        currentIndex++
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentWord != null) {
            LearnWordSection(
                word = currentWord,
                onAnswerGiven = onAnswerGiven,
                onNextQuestion = onNextQuestion
            )
        } else {
            Text("Поздравляем! Вы изучили все слова.")
        }
    }
}

@Composable
fun LearnWordSection(
    word: WordEntity,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit
) {
    val userAnswer = rememberSaveable {
        mutableStateOf("")
    }

    val isUserCorrect = rememberSaveable {
        mutableStateOf<Boolean?>(null)
    }

    val checkButtonText = rememberSaveable {
        mutableStateOf("")
    }
    val checkButtonColor = remember {
        mutableStateOf<ButtonColors?>(null)
    }
    val checkStr = stringResource(id = R.string.check)
    val nextStr = stringResource(id = R.string.next)

    LaunchedEffect(key1 = word) {
        isUserCorrect.value = null
        userAnswer.value = ""
        checkButtonText.value = checkStr
        checkButtonColor.value = null
    }

    val buttonColorGreen = ButtonDefaults.buttonColors(Color.Green)
    val buttonColorRed = ButtonDefaults.buttonColors(Color.Red)

    LaunchedEffect(isUserCorrect.value) {
        if (isUserCorrect.value != null) {
            checkButtonText.value = nextStr
            checkButtonColor.value = if(isUserCorrect.value!!) buttonColorGreen else buttonColorRed
        }
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stringResource(id = R.string.translate)}: ${word.key}",
        )

        StyledTextField(
            textState = userAnswer,
            label = stringResource(id = R.string.input_german)
        )

        StyledButton(
            onClick = {
                if (isUserCorrect.value == null) {
                    val isCorrect = userAnswer.value == word.wordValue

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