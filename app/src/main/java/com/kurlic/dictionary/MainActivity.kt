package com.kurlic.dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kurlic.dictionary.data.WordDao
import com.kurlic.dictionary.data.WordDatabase
import com.kurlic.dictionary.screens.AppNavigation
import com.kurlic.dictionary.screens.learnwords.traindata.TrainData
import com.kurlic.dictionary.screens.learnwords.traindata.TrainViewModel
import com.kurlic.dictionary.ui.theme.DictionaryTheme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var dao: WordDao
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dao = WordDatabase.getDatabase(applicationContext).wordDao()
        val vm: TrainViewModel by viewModels()
        vm.setTrainData(TrainData(mutableListOf()))

        setContent {
            DictionaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}