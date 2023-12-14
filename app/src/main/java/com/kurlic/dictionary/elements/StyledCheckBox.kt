package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kurlic.dictionary.R
import com.kurlic.dictionary.ui.theme.LightGray
import com.kurlic.dictionary.ui.theme.MediumGray

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
            checkedColor = LightGray,
            checkmarkColor = MediumGray
        )
    )
}