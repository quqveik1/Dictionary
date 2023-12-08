package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kurlic.dictionary.R

@Composable
fun StyledText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = dimensionResource(id = R.dimen.text_size_standard).value.sp,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_standard))
    )
}