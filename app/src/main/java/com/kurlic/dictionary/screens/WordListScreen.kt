package com.kurlic.dictionary.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kurlic.dictionary.R
import com.kurlic.dictionary.common.AnimationLen
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.data.WordListViewModel
import com.kurlic.dictionary.data.getLangDrawableId
import com.kurlic.dictionary.data.getLangStringId
import com.kurlic.dictionary.elements.styled.StyledCard
import com.kurlic.dictionary.elements.styled.StyledText
import kotlinx.coroutines.delay

const val WordListScreenTag = "WORDLIST"

@Composable
fun WordListScreen(
    navController: NavController,
    wordListViewModel: WordListViewModel
) {
    val isLoading by wordListViewModel.isLoading.observeAsState(true)
    val words by wordListViewModel.words.observeAsState(listOf())
    var activeCardId by rememberSaveable { mutableStateOf<Long?>(null) }

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
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(words,
                key = { word -> word.id!! }) { word ->
                DrawWord(
                    word = word,
                    wordListViewModel = wordListViewModel,
                    activeCardId = activeCardId,
                    setActiveCardId = { id -> activeCardId = id })
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawWord(
    word: WordEntity,
    wordListViewModel: WordListViewModel,
    activeCardId: Long?,
    setActiveCardId: (Long?) -> Unit
) {
    val haptics = LocalHapticFeedback.current

    val isVisible = rememberSaveable { mutableStateOf(true) }

    val showDelete = activeCardId?.toInt() == word.id
    val showedDelete = rememberSaveable { mutableStateOf(showDelete) }
    val animatedWeight by animateFloatAsState(
        targetValue = if (showedDelete.value) 0.5f else 0f,
        animationSpec = tween(durationMillis = AnimationLen),
        label = "delete_animation"
    )

    LaunchedEffect(showDelete) {
        showedDelete.value = showDelete
    }

    AnimatedVisibility(
        visible = isVisible.value,
        exit = shrinkVertically(animationSpec = tween(durationMillis = AnimationLen)) + fadeOut(animationSpec = tween(durationMillis = AnimationLen)),
        modifier = Modifier.animateContentSize(animationSpec = tween(durationMillis = AnimationLen))
    ) {
        StyledCard(
            modifier = Modifier.combinedClickable(onClick = {},
                onLongClick = {
                    setActiveCardId(if (showDelete) null else word.id!!.toLong())
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                })
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = getLangDrawableId(word.keyLang)),
                    contentDescription = stringResource(id = getLangStringId(word.keyLang)),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.text_size_standard))
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_small),
                            end = dimensionResource(id = R.dimen.padding_small)
                        )
                        .weight(0.5f)
                )
                StyledText(
                    text = word.key,
                    modifier = Modifier.weight(1f)
                )
                Divider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Image(
                    painter = painterResource(id = getLangDrawableId(word.valueLang)),
                    contentDescription = stringResource(id = getLangStringId(word.valueLang)),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.text_size_standard))
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_small),
                            end = dimensionResource(id = R.dimen.padding_small)
                        )
                        .weight(0.5f)
                )
                StyledText(
                    text = word.wordValue,
                    modifier = Modifier.weight(1f)
                )
                if (animatedWeight > 0) {
                    Divider(
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    IconButton(modifier = Modifier.weight(animatedWeight),
                        onClick = {
                            isVisible.value = false
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete)
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(isVisible.value) {
        if (!isVisible.value) {
            delay(AnimationLen.toLong())
            wordListViewModel.deleteWord(word)
        }
    }
}
