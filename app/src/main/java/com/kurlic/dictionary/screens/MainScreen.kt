package com.kurlic.dictionary.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.elements.styled.StyledButton

const val MainScreenTag = "MAIN"

@Composable
fun MainScreen(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_standard)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp,
            textAlign = TextAlign.Center,
            lineHeight = dimensionResource(id = R.dimen.text_size_big).value.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        StyledButton(text = stringResource(id = R.string.start_training),
            onClick = { navController!!.navigate(TypeScreenTag) })
    }
}
