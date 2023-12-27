package com.kurlic.dictionary.screens.learnwords

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.elements.common.LanguageSpinner
import com.kurlic.dictionary.elements.styled.StyledButton
import com.kurlic.dictionary.elements.styled.StyledCheckBox
import com.kurlic.dictionary.elements.styled.StyledDivider
import com.kurlic.dictionary.elements.styled.StyledText
import com.kurlic.dictionary.elements.styled.StyledTextField
import com.kurlic.dictionary.screens.learnwords.train.LearnWordsScreenTag
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.TrainTypes
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel

const val LearnModeScreenTag = "MODE"

@Composable
fun LearnModeScreen(
    navController: NavController,
    trainViewModel: TrainViewModel,
    wordListViewModel: WordListViewModel
) {
    var trainWordsLen by rememberSaveable {
        mutableIntStateOf(10)
    }
    val trainWordsLenInput = rememberSaveable {
        mutableStateOf("$trainWordsLen")
    }
    LaunchedEffect(trainWordsLenInput.value) {
        trainWordsLen = if (trainWordsLenInput.value.isNotEmpty()) {
            trainWordsLenInput.value.toInt()
        } else {
            0
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
        val keyLangName = rememberSaveable() {
            mutableStateOf(LangName.Russian)
        }
        val valueLangName = rememberSaveable() {
            mutableStateOf(LangName.English)
        }

        LanguageSpinner(
            name = R.string.key_language,
            langName = keyLangName
        )

        StyledDivider()

        LanguageSpinner(
            name = R.string.value_language,
            langName = valueLangName
        )

        StyledDivider()

        val cardTrainType = rememberSaveable { mutableStateOf(TrainTypes.Cards) }
        val cardTypeOptions = mapOf(
            stringResource(id = R.string.flip_cards) to TrainTypes.Cards,
            stringResource(id = R.string.writing) to TrainTypes.Writing,
            stringResource(id = R.string.answer_variants) to TrainTypes.AnswerVariants
        )
        RadioGroupSection(
            options = cardTypeOptions,
            selectedOption = cardTrainType,
            labelText = stringResource(id = R.string.card_train_type)
        )

        StyledDivider()

        val wordSetType = rememberSaveable { mutableStateOf(WordSetType.New) }
        val wordSetTypeOptions = mapOf(
            stringResource(id = R.string.new_type) to WordSetType.New,
            stringResource(id = R.string.old_type) to WordSetType.Old,
            stringResource(id = R.string.random_type) to WordSetType.Random
        )
        RadioGroupSection(
            options = wordSetTypeOptions,
            selectedOption = wordSetType,
            labelText = stringResource(id = R.string.word_set_type)
        )

        StyledDivider()

        val isLearnByKeyChecked = rememberSaveable {
            mutableStateOf(true)
        }
        StudyByKeysSection(isLearnByKeyChecked = isLearnByKeyChecked)

        StyledDivider()

        InputWordCountSection(trainWordsLenInput = trainWordsLenInput)

        val context = LocalContext.current

        StyledButton(text = stringResource(id = R.string.study),
            onClick = {
                goToLearnScreen(
                    wordListViewModel,
                    trainViewModel,
                    navController,
                    isLearnByKeyChecked.value,
                    cardTrainType.value,
                    trainWordsLen,
                    wordSetType.value,
                    keyLangName.value,
                    valueLangName.value,
                    context
                )
            })
    }
}

enum class WordSetType {
    New, Old, Random
}

@Composable
fun <T> RadioGroupSection(
    options: Map<String, T>,
    selectedOption: MutableState<T>,
    labelText: String
) {
    Column {
        StyledText(text = labelText)
        options.forEach { (label, value) ->
            Row {
                RadioButton(
                    selected = value == selectedOption.value,
                    onClick = { selectedOption.value = value },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.light_gray))
                )
                StyledText(text = label)
            }
        }
    }
}

@Composable
fun StudyByKeysSection(isLearnByKeyChecked: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        StyledCheckBox(checked = isLearnByKeyChecked.value,
            onCheckedChange = { isLearnByKeyChecked.value = it })
        StyledText(text = stringResource(id = R.string.study_by_keys))
    }
}

@Composable
fun InputWordCountSection(trainWordsLenInput: MutableState<String>) {
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
}

private fun goToLearnScreen(
    wordListViewModel: WordListViewModel,
    trainViewModel: TrainViewModel,
    navController: NavController,
    learnByKey: Boolean,
    trainTypes: TrainTypes,
    trainWordsLen: Int,
    wordSetType: WordSetType,
    keyLangName: LangName,
    valueLangName: LangName,
    context: Context
) {
    if (trainWordsLen == 0) {
        Toast.makeText(
            context,
            context.getString(R.string.input_fields_are_incorrect),
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    val trainData = buildWordsSet(
        wordListViewModel,
        learnByKey,
        trainTypes,
        trainWordsLen,
        wordSetType,
        keyLangName,
        valueLangName
    )

    if (trainData == null) {
        Toast.makeText(
            context,
            context.getString(R.string.input_fields_are_incorrect),
            Toast.LENGTH_SHORT
        ).show()
        return
    }

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
    trainWordsLen: Int,
    wordSetType: WordSetType,
    keyLangName: LangName,
    valueLangName: LangName
): TrainData? {
    val allWords = wordListViewModel.words.value
        ?.filter { it.keyLang == keyLangName && it.valueLang == valueLangName }
        ?: return null
    if (allWords.size < trainWordsLen) return null

    val learningWords = when (wordSetType) {
        WordSetType.Random -> allWords.shuffled().take(trainWordsLen)
        WordSetType.New -> allWords.sortedBy { it.learningProgress }
            .take(trainWordsLen)

        WordSetType.Old -> {
            val sortedWords = allWords.sortedBy { it.learningProgress }
            val median = sortedWords[sortedWords.size / 2].learningProgress

            sortedWords.filter { it.learningProgress > median }.shuffled().take(trainWordsLen)
        }
    }

    return TrainData(
        words = learningWords,
        learnByKey = learnByKey,
        trainTypes = trainTypes
    )
}
