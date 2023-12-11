package com.kurlic.dictionary.screens.learnwords

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.kurlic.dictionary.data.WordEntity

@Parcelize
data class TrainData(
    val words: List<WordEntity>,
    val learnedWords: MutableList<WordEntity> = mutableListOf()
) : Parcelable {}