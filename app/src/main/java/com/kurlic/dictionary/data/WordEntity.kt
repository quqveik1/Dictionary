package com.kurlic.dictionary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "keyLang") val keyLang: LangName,
    @ColumnInfo(name = "wordValue") val wordValue: String,
    @ColumnInfo(name = "valueLang") val valueLang: LangName,
    @ColumnInfo(name = "learningProgress") val learningProgress: Int,
    @ColumnInfo(name = "category") val category: WordCategory,

)
