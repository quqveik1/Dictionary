package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.kurlic.dictionary.R
import com.kurlic.dictionary.ui.theme.LightGray


@Composable
fun StyledLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_standard)),
    color: Color = LightGray
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = color
    )
}