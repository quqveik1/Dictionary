package com.kurlic.dictionary.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kurlic.dictionary.elements.StyledButton

const val TypeScreenTag = "TYPE"

@Composable
fun TypeScreen(navController: NavController) {
    val StudyCurrentWords = "Study current words"
    val RepeatOldWords = "Repeat old words"
    val StudyCustomSet = "Study custom set of words"
    val AddWord = "Add new word"
    val itemList = listOf(
        StudyCurrentWords,
        RepeatOldWords,
        StudyCustomSet,
        AddWord
    )
    LazyColumn() {
        items(itemList) { item ->
            val onClickFnc = when (item) {
                AddWord -> {
                    { navController.navigate(NewWordScreenTag) }
                }

                else -> {{}}
            }
            StyledButton(
                text = item,
                onClick = onClickFnc,
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }


}