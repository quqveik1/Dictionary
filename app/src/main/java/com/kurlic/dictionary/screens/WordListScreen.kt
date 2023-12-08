package com.kurlic.dictionary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kurlic.dictionary.MainActivity
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledText
import kotlinx.coroutines.launch

const val WordListScreenTag = "WORDLIST"

@Preview
@Composable
fun WordListScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var words by remember { mutableStateOf(listOf<WordEntity>()) }
    var isLoading by remember { mutableStateOf(true) }

    val wordDao = MainActivity.dao

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            words = wordDao.getAllWords()
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.load),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(id = R.dimen.text_size_big).value.sp
            )
        }
    } else {
        LazyColumn {
            items(words) { word ->
                DrawWord(word = word)
            }
        }
    }
}

@Composable
fun DrawWord(word: WordEntity) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_standard))
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_standard)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            StyledText(
                text = word.key,
                modifier = Modifier.weight(1f)
            )
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            StyledText(
                text = word.wordValue,
                modifier = Modifier.weight(1f)
            )
        }
    }
}