package com.kurlic.dictionary.screens.learnwords

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

const val TestLearnWordsWriteScreenTag = "TEST"

@Composable
fun TestLearnWordsWriteScreen(
    navController: NavController,
    trainViewModel: TrainViewModel
) {
    val words = listOf(
        WordEntity(
            id = 1,
            key = "Дом",
            keyLang = LangName.Russian,
            wordValue = "Haus",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        ),
        WordEntity(
            id = 2,
            key = "Кошка",
            keyLang = LangName.Russian,
            wordValue = "Katze",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        ),
        WordEntity(
            id = 3,
            key = "Солнце",
            keyLang = LangName.Russian,
            wordValue = "Sonne",
            valueLang = LangName.German,
            learningProgress = 0,
            category = WordCategory.Common
        )
    )
    var wasCreated by rememberSaveable {
        mutableStateOf(false)
    }

    if (!wasCreated) {
        trainViewModel.setTrainData(TrainData(words))
        wasCreated = true
    }

    LearnWordsWriteScreen(
        navController = navController,
        trainViewModel
    )
}

fun calcProgress(
    words: List<WordEntity>,
    currentIndex: Int
): Float {
    return currentIndex.toFloat() / words.size.toFloat();
}

const val LearnWordsWriteScreenTag = "WRITE"

@Composable
fun LearnWordsWriteScreen(
    navController: NavController,
    trainViewModel: TrainViewModel
) {
    val trainData by trainViewModel.trainData.observeAsState()
    var currentIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val currentWord = trainData!!.words.getOrNull(currentIndex)

    val progress = rememberSaveable(
        currentIndex,
        trainData!!.words.size
    ) {
        calcProgress(
            trainData!!.words,
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
            trainData!!.learnedWords.add(currentWord)
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
                    onNextQuestion = onNextQuestion,
                    trainData!!.learnByKey
                )
            } else {
                navController.navigate(FinalScreenTag) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                }
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