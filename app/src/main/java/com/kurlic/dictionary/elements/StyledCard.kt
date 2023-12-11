package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.kurlic.dictionary.R

@Composable
fun StyledCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_standard))
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_standard)),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        content()
    }
}