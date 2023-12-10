package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R
import com.kurlic.dictionary.ui.theme.MediumGray

@Composable
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors? = null
) {
    Button(
        onClick = onClick,
        colors = if(buttonColors != null) buttonColors else ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
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
