package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.kurlic.dictionary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    textState: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onlyNumbers: Boolean = false
) {
    TextField(
        value = textState.value,
        onValueChange = { newText ->
            if (newText.all { it.isDigit() } || !onlyNumbers) {
                textState.value = newText
            }
        },
        label = { Text(label) },
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_standard))
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_standard)),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = if (!onlyNumbers) KeyboardType.Text else KeyboardType.Number
        ),
        enabled = enabled
    )
}