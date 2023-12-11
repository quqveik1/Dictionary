package com.kurlic.dictionary.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: WordEntity)

    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWords(): List<WordEntity>

    @Query("DELETE FROM WordEntity WHERE id = :wordId")
    suspend fun deleteWord(wordId: Int)

    @Query("SELECT * FROM WordEntity WHERE id = :wordId")
    suspend fun getWordById(wordId: Int): WordEntity?

    @Update
    suspend fun updateWord(word: WordEntity)
}