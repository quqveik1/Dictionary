package com.kurlic.dictionary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.elements.styled.StyledButton
import com.kurlic.dictionary.screens.learnwords.LearnModeScreenTag

const val TypeScreenTag = "TYPE"

@Composable
fun TypeScreen(navController: NavController) {
    val StudyWords = stringResource(id = R.string.study_words)
    val ViewListOfWords = stringResource(id = R.string.view_list_words)
    val AddWord = stringResource(id = R.string.add_new_words)
    val itemList = listOf(
        StudyWords,
        ViewListOfWords,
        AddWord
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(itemList) { item ->
                val onClickFnc = when (item) {
                    AddWord -> {
                        { navController.navigate(NewWordScreenTag) }
                    }

                    ViewListOfWords -> {
                        { navController.navigate(WordListScreenTag) }
                    }

                    StudyWords -> {
                        { navController.navigate(LearnModeScreenTag) }
                    }

                    else -> {
                        {}
                    }
                }
                StyledButton(
                    text = item,
                    onClick = onClickFnc,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
