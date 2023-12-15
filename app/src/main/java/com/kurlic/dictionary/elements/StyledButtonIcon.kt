package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import com.kurlic.dictionary.R

@Composable
fun StyledButtonIcon(
    iconId: Int,
    onClick: () -> Unit,
    text: String = "",
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors? = null,
) {
    val icon = ImageVector.vectorResource(id = iconId)
    Button(
        onClick = onClick,
        colors = buttonColors ?: ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_standard)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_standard)),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )
    }
}