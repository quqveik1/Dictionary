package com.kurlic.dictionary.elements.styled

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.kurlic.dictionary.R

@Composable
fun StyledCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_standard))
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = CheckboxDefaults.colors(
            checkedColor = colorResource(id = R.color.light_gray),
            checkmarkColor = colorResource(id = R.color.medium_gray)
            )
    )
}