package com.kurlic.dictionary.screens.learnwords.traindata

import android.os.Parcelable
import com.kurlic.dictionary.data.WordEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainData(
    val words: List<WordEntity>,
    val learnedWords: MutableList<WordEntity> = mutableListOf(),
    val learnByKey: Boolean = true,
    val trainTypes: TrainTypes = TrainTypes.Writing
) : Parcelable {}