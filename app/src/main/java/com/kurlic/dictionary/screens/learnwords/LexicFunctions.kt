package com.kurlic.dictionary.screens.learnwords

import java.util.Locale

fun isAnswerSame(
    dest: String,
    input: String
): Boolean {
    val normalizedDest = dest.lowercase(Locale.getDefault()).trim().replace(
        "\\s+".toRegex(),
        " "
    )
    val normalizedInput = input.lowercase(Locale.getDefault()).trim().replace(
        "\\s+".toRegex(),
        " "
    )
    return normalizedDest == normalizedInput
}