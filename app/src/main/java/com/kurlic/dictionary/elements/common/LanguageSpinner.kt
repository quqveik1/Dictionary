package com.kurlic.dictionary.elements.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.LangName
import com.kurlic.dictionary.data.getLangStringId
import com.kurlic.dictionary.data.getListOfLangString
import com.kurlic.dictionary.elements.styled.StyledSpinner
import com.kurlic.dictionary.elements.styled.StyledText


@Composable
fun LanguageSpinner(
    name: Int,
    langName: MutableState<LangName>
) {
    val onLangListSelected: (Int) -> LangName = { index ->
        when (index) {
            0 -> {
                LangName.Russian
            }

            1 -> {
                LangName.English
            }

            else -> {
                LangName.German
            }
        }
    }

    val langList = getListOfLangString(LocalContext.current)

    val defaultPos = langList.indexOf(stringResource(id = getLangStringId(langName.value)))

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StyledText(text = stringResource(id = name) + ": ")
        StyledSpinner(
            items = langList,
            onItemSelected = { num ->
                langName.value = onLangListSelected(num)
            },
            defaultPosition = defaultPos
        )
    }
}
