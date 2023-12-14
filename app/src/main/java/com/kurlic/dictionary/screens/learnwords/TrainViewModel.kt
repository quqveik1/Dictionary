package com.kurlic.dictionary.screens.learnwords

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrainViewModel : ViewModel() {
    private val _trainData = MutableLiveData(TrainData(mutableListOf()))
    val trainData: LiveData<TrainData> = _trainData

    fun setTrainData(data: TrainData) {
        _trainData.value = data
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("TrainViewModel", "TrainViewModel cleared")
    }
}