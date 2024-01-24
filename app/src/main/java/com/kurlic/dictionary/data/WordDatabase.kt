package com.kurlic.dictionary.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@Database(
    entities = [WordEntity::class],
    version = 1
)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "name"
                ).addCallback(WordDatabaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback() : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        fillDb(database.wordDao())
                    }
                }
            }
        }

        suspend fun fillDb(
            wordDao: WordDao
        ) {
            val engWords = listOf(
                WordEntity(null, "Машина", LangName.Russian, "Car", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Город", LangName.Russian, "City", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Еда", LangName.Russian, "Food", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Вода", LangName.Russian, "Water", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Друг", LangName.Russian, "Friend", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Книга", LangName.Russian, "Book", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Телефон", LangName.Russian, "Phone", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Игра", LangName.Russian, "Game", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Улица", LangName.Russian, "Street", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Музыка", LangName.Russian, "Music", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Погода", LangName.Russian, "Weather", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Школа", LangName.Russian, "School", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Университет", LangName.Russian, "University", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Компьютер", LangName.Russian, "Computer", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Природа", LangName.Russian, "Nature", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Животное", LangName.Russian, "Animal", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Ресторан", LangName.Russian, "Restaurant", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Фильм", LangName.Russian, "Movie", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Спорт", LangName.Russian, "Sport", LangName.English, 0, WordCategory.Common),
                WordEntity(null, "Путешествие", LangName.Russian, "Travel", LangName.English, 0, WordCategory.Common)
            )

            val germanWords = listOf(
                WordEntity(null, "Машина", LangName.Russian, "Auto", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Город", LangName.Russian, "Stadt", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Еда", LangName.Russian, "Essen", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Вода", LangName.Russian, "Wasser", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Друг", LangName.Russian, "Freund", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Книга", LangName.Russian, "Buch", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Телефон", LangName.Russian, "Telefon", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Игра", LangName.Russian, "Spiel", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Улица", LangName.Russian, "Straße", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Музыка", LangName.Russian, "Musik", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Погода", LangName.Russian, "Wetter", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Школа", LangName.Russian, "Schule", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Университет", LangName.Russian, "Universität", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Компьютер", LangName.Russian, "Computer", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Природа", LangName.Russian, "Natur", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Животное", LangName.Russian, "Tier", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Ресторан", LangName.Russian, "Restaurant", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Фильм", LangName.Russian, "Film", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Спорт", LangName.Russian, "Sport", LangName.German, 0, WordCategory.Common),
                WordEntity(null, "Путешествие", LangName.Russian, "Reise", LangName.German, 0, WordCategory.Common)
            )


            for (w in (engWords + germanWords)) {
                wordDao.insertWord(w)
            }
        }
    }
}