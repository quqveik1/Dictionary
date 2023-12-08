package com.kurlic.dictionary.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.elements.StyledButton
import com.kurlic.dictionary.elements.StyledTextField

const val NewWordScreenTag = "NEWWORD"

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewWordScreen(navController: NavController?) {
    val russianText = rememberSaveable { mutableStateOf("") }
    val germanText = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_standard)),
        verticalArrangement = Arrangement.Center
    ) {
        StyledTextField(
            textState = russianText,
            label = stringResource(id = R.string.input_russian),
        )

        StyledTextField(
            textState = germanText,
            label = stringResource(id = R.string.input_english),
        )

    }
}

