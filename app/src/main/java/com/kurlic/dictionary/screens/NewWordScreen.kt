package com.kurlic.dictionary.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordCategory
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledSpinner
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.elements.StyledTextField

const val NewWordScreenTag = "NEWWORD"

@Composable
fun NewWordScreen(
    navController: NavController,
    wordListViewModel: WordListViewModel
) {
    val russianText = rememberSaveable { mutableStateOf("") }
    val germanText = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val keyLang = rememberSaveable { mutableStateOf(LangName.Russian) }
    val valueLang = rememberSaveable { mutableStateOf(LangName.Russian) }

    val onLangListSelected: (Int) -> LangName = { index ->
        when (index) {
            0 -> {
                LangName.Russian
            }

            1 -> {
                LangName.English
            }

            else -> {
                LangName.German
            }
        }
    }

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_standard)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.input_word),
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StyledText(text = stringResource(id = R.string.key_language) + ": ")
            StyledSpinner(items = listOf(
                stringResource(id = R.string.russian),
                stringResource(id = R.string.english),
                stringResource(id = R.string.german)
            ),
                onItemSelected = { num ->
                    keyLang.value = onLangListSelected(num)
                })
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StyledText(text = stringResource(id = R.string.value_language) + ": ")
            StyledSpinner(items = listOf(
                stringResource(id = R.string.russian),
                stringResource(id = R.string.english),
                stringResource(id = R.string.german)
            ),
                onItemSelected = { num ->
                    valueLang.value = onLangListSelected(num)
                })
        }
        StyledTextField(
            textState = russianText,
            label = stringResource(id = R.string.input_key),
            modifier = Modifier.fillMaxWidth()
        )

        StyledTextField(
            textState = germanText,
            label = stringResource(id = R.string.input_value),
            modifier = Modifier.fillMaxWidth()
        )

        StyledButton(text = stringResource(id = R.string.add_word),
            onClick = {
                addNewWord(
                    germanText,
                    russianText,
                    keyLang.value,
                    valueLang.value,
                    context,
                    wordListViewModel
                )
            })

    }
}

fun checkCorrectness(
    germanText: String,
    russianText: String
): Boolean {
    return !germanText.isEmpty() && !russianText.isEmpty()
}

fun addNewWord(
    germanText: MutableState<String>,
    russianText: MutableState<String>,
    keyLangName: LangName,
    valueLangName: LangName,
    context: Context,
    wordListViewModel: WordListViewModel
) {
    if (!checkCorrectness(
            germanText.value,
            russianText.value
        )
    ) {
        Toast.makeText(
            context,
            context.getString(R.string.input_fields_are_incorrect),
            Toast.LENGTH_SHORT
        ).show()
        return
    }

    val word = WordEntity(
        null,
        String(russianText.value.toCharArray()),
        keyLangName,
        String(germanText.value.toCharArray()),
        valueLangName,
        0,
        WordCategory.Learning
    )

    wordListViewModel.addWord(word)
    germanText.value = ""
    russianText.value = ""
}
