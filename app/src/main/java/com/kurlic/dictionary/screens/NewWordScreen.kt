package com.kurlic.dictionary.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kurlic.dictionary.MainActivity
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.WordCategory
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val NewWordScreenTag = "NEWWORD"

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewWordScreen(navController: NavController?) {
    val russianText = rememberSaveable { mutableStateOf("") }
    val germanText = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_standard)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.input_word),
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
        )
        StyledTextField(
            textState = russianText,
            label = stringResource(id = R.string.input_russian),
        )

        StyledTextField(
            textState = germanText,
            label = stringResource(id = R.string.input_german),
        )

        StyledButton(text = stringResource(id = R.string.add_word),
            onClick = {
                addNewWord(
                    germanText,
                    russianText,
                    context,
                    scope
                )
            })

    }
}

fun checkCorrectness(
    germanText: MutableState<String>,
    russianText: MutableState<String>
): Boolean {
    if (!germanText.value.isEmpty() && !russianText.value.isEmpty()) {
        return true
    }
    return false
}

fun addNewWord(
    germanText: MutableState<String>,
    russianText: MutableState<String>,
    context: Context,
    scope: CoroutineScope
) {
    if (!checkCorrectness(
            germanText,
            russianText
        )
    ) {
        Toast.makeText(
            context,
            "Input fields are incorrect",
            Toast.LENGTH_SHORT
        ).show()
        return
    }

    val word = WordEntity(
        null,
        russianText.value,
        LangName.Russian,
        germanText.value,
        LangName.German,
        0,
        WordCategory.Learning
    )
    scope.launch {
        val words = MainActivity.dao.getAllWords()
        Toast.makeText(context, words.toString(), Toast.LENGTH_SHORT).show()
        MainActivity.dao.insertWord(word)
        germanText.value = ""
        russianText.value = ""
    }
}

