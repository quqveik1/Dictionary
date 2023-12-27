package com.kurlic.dictionary.elements.styled

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R

@Composable
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors? = null
) {
    Button(
        onClick = onClick,
        colors = buttonColors ?: ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_standard)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_standard)),
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.text_size_standard).value.sp
        )
    }
}

@Composable
fun getButtonColor() : ButtonColors {
    return ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
}
