package com.kurlic.dictionary.screens.learnwords.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordCategory
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.elements.StyledLinearProgressIndicator
import com.kurlic.dictionary.screens.learnwords.FinalScreenTag
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.TrainTypes
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel

const val TestLearnWordsWriteScreenTag = "TEST"

@Composable
fun TestLearnWordsWriteScreen(
    navController: NavController,
    trainViewModel: TrainViewModel,
    wordListViewModel: WordListViewModel
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

    LearnWordsScreen(
        navController = navController,
        trainViewModel = trainViewModel,
        wordListViewModel = wordListViewModel
    )
}

fun calcProgress(
    words: List<WordEntity>,
    currentIndex: Int
): Float {
    return currentIndex.toFloat() / words.size.toFloat();
}

const val LearnWordsScreenTag = "WORDS"

@Composable
fun LearnWordsScreen(
    navController: NavController,
    trainViewModel: TrainViewModel,
    wordListViewModel: WordListViewModel
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

    val onAnswerGiven: (isCorrect: Boolean) -> Unit = { isCorrect: Boolean ->
        Unit
        if (currentWord != null && isCorrect) {
            wordListViewModel.updateWordByProgress(
                currentWord,
                currentWord.learningProgress + 1
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
        StyledLinearProgressIndicator(progress = progress)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentWord != null) {
                if (trainData!!.trainTypes == TrainTypes.Writing) {
                    LearnByWritingSection(
                        word = currentWord,
                        onAnswerGiven = onAnswerGiven,
                        onNextQuestion = onNextQuestion,
                        isLearnByKey = trainData!!.learnByKey
                    )
                }
                else if(trainData!!.trainTypes == TrainTypes.Cards) {
                    LearnByCardsSection(
                        word = currentWord,
                        onAnswerGiven = onAnswerGiven,
                        onNextQuestion = onNextQuestion,
                        isLearnByKey = trainData!!.learnByKey
                    )
                } else {
                    LearnByAnswerVariantsSection(
                        word = currentWord,
                        trainData = trainData!!,
                        onAnswerGiven = onAnswerGiven,
                        onNextQuestion = onNextQuestion,
                        isLearnByKey = trainData!!.learnByKey
                    )
                }
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
