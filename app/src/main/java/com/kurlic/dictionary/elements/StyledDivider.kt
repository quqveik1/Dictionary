package com.kurlic.dictionary.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kurlic.dictionary.R

@Composable
fun StyledDivider() {
    Divider(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_standard)))
}