package com.kurlic.dictionary.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordListViewModel(private val wordDao: WordDao) : ViewModel() {
    private val _words = MutableLiveData<List<WordEntity>>()
    val words: LiveData<List<WordEntity>> = _words
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            _words.value = wordDao.getAllWords()
            _isLoading.value = false
        }
    }

    fun deleteWord(word: WordEntity) {
        viewModelScope.launch {
            wordDao.deleteWord(word.id!!)
            loadWords()
        }
    }

    fun addWord(word: WordEntity) {
        viewModelScope.launch {
            wordDao.insertWord(word)
            loadWords()
        }
    }

    fun updateWordByProgress(
        word: WordEntity,
        newProgress: Int
    ) {
        val updatedWord = word.copy(learningProgress = newProgress)
        viewModelScope.launch {
            if (updatedWord.id != null) {
                wordDao.updateWord(updatedWord)
            }
        }
    }

    fun updateWord(word: WordEntity) {
        val updatedWord = word.copy()
        viewModelScope.launch {
            if (updatedWord.id != null) {
                wordDao.updateWord(updatedWord)
            }
        }
    }

}