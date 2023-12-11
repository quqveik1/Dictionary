package com.kurlic.dictionary.screens.learnwords

import android.widget.ProgressBar
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.kurlic.dictionary.MainActivity
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordCategory
import com.kurlic.dictionary.data.WordDao
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledCard
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.elements.StyledTextField
import com.kurlic.dictionary.ui.theme.CorrectGreen
import com.kurlic.dictionary.ui.theme.ErrorRed
import com.kurlic.dictionary.ui.theme.LightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        _trainData = TrainData(words)
    )
}

fun calcProgress(
    words: List<WordEntity>,
    currentIndex: Int
): Float {
    return currentIndex.toFloat() / words.size.toFloat();
}

@Composable
fun LearnWordsScreen(
    _trainData: TrainData
) {
    val trainData = rememberSaveable {
        _trainData
    }
    var currentIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val currentWord = trainData.words.getOrNull(currentIndex)

    val progress = rememberSaveable(
        currentIndex,
        trainData.words.size
    ) {
        calcProgress(
            trainData.words,
            currentIndex
        )
    }

    val scope = rememberCoroutineScope()

    val onAnswerGiven: (isCorrect: Boolean) -> Unit = { isCorrect: Boolean ->
        Unit
        if (currentWord != null && isCorrect) {
            updateLearningProgress(
                currentWord,
                currentWord.learningProgress + 1,
                MainActivity.dao,
                scope
            )
            trainData.learnedWords.add(currentWord)
        }
    }
    val onNextQuestion: () -> Unit = {
        currentIndex++
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_standard)),
            color = LightGray
        )
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
                val navController = rememberNavController()
                val gson = Gson()
                val trainDataJson = gson.toJson(trainData)
                navController.navigate("$FinalScreenTag/$trainDataJson")
            }
        }
    }
}

fun updateLearningProgress(
    word: WordEntity,
    newProgress: Int,
    dao: WordDao,
    scope: CoroutineScope
) {
    scope.launch {
        word.let {
            val updatedWord = it.copy(learningProgress = newProgress)
            if (updatedWord.id != null) {
                dao.updateWord(updatedWord)
            }
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
            text = word.key,
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
        )

        if (isUserCorrect.value != null && !isUserCorrect.value!!) {
            StyledCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                backgroundColor = CorrectGreen
            ) {
                StyledText(text = word.wordValue)

            }
        }

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