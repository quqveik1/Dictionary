package com.kurlic.dictionary.data

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.kurlic.dictionary.R

enum class LangName(val value: Int) {
    Russian(0), English(1), German(2)
}

fun getLangDrawableId(langName: LangName): Int {
    return when (langName) {
        LangName.Russian -> R.drawable.russia
        LangName.English -> R.drawable.united_kingdom
        LangName.German -> R.drawable.germany
    }
}

fun getLangStringId(langName: LangName): Int {
    return when (langName) {
        LangName.Russian -> R.string.russian
        LangName.English -> R.string.english
        LangName.German -> R.string.german
    }
}

fun getListOfLangString(context: Context): List<String> {
    return listOf(
        context.getString(R.string.russian),
        context.getString(R.string.english),
        context.getString(R.string.german)
    )
}