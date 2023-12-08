package com.kurlic.dictionary.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: WordEntity)

    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWords(): List<WordEntity>
}