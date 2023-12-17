package com.kurlic.dictionary.screens.learnwords.train

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.kurlic.dictionary.R
import com.kurlic.dictionary.data.WordEntity
import com.kurlic.dictionary.elements.StyledButtonIcon
import com.kurlic.dictionary.elements.StyledText
import com.kurlic.dictionary.screens.learnwords.traindata.getGivenStringFromWord
import com.kurlic.dictionary.screens.learnwords.traindata.getLearnStringFromWord

@Composable
fun LearnByCardsSection(
    word: WordEntity,
    onAnswerGiven: (isCorrect: Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    isLearnByKey: Boolean
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (cardBox, buttonRow) = createRefs()

        var flipped by remember { mutableStateOf(false) }
        BoxWithConstraints(modifier = Modifier
            .constrainAs(cardBox) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .clickable { flipped = !flipped },
            contentAlignment = Alignment.Center
        ) {
            val rotateYPos by animateFloatAsState(
                targetValue = if (flipped) 180f else 0f
            )
            val cardScreenRatio = 0.6f
            val cameraDistanceGraphic = 12f * minOf(
                LocalConfiguration.current.screenWidthDp,
                LocalConfiguration.current.screenHeightDp
            )
            val constraintSize = minOf(
                maxWidth,
                maxHeight
            )

            Card(modifier = Modifier
                .size(constraintSize * cardScreenRatio)
                .graphicsLayer {
                    rotationY = rotateYPos
                    cameraDistance = cameraDistanceGraphic
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (rotateYPos <= 90f) {
                        StyledText(text = getLearnStringFromWord(word, isLearnByKey),
                            modifier = Modifier.graphicsLayer {
                                rotationY = rotateYPos
                                cameraDistance = cameraDistanceGraphic
                            })
                    } else {
                        StyledText(text = getGivenStringFromWord(word, isLearnByKey),
                            modifier = Modifier.graphicsLayer {
                                rotationY = rotateYPos
                                cameraDistance = cameraDistanceGraphic
                            })
                    }
                }
            }
        }
        Row(modifier = Modifier
            .constrainAs(buttonRow) {
                start.linkTo(parent.start)
                top.linkTo(cardBox.bottom)
                end.linkTo(parent.end)
            }
            .padding(dimensionResource(id = R.dimen.padding_standard)),
            horizontalArrangement = Arrangement.Center) {
            StyledButtonIcon(
                iconId = R.drawable.yes_icon,
                text = stringResource(id = R.string.i_know),
                onClick = {
                    onAnswerGiven(true)
                    onNextQuestion()
                    flipped = false
                },
            )

            StyledButtonIcon(
                iconId = R.drawable.no_icon,
                text = stringResource(id = R.string.i_dont_know),
                onClick = {
                    onAnswerGiven(false)
                    onNextQuestion()
                    flipped = false
                },
            )
        }

    }
}