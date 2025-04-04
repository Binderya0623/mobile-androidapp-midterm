package com.example.midterm22b1num0027.repository

import android.content.Context
import com.example.midterm22b1num0027.data.Word
import com.example.midterm22b1num0027.data.WordDao
import com.example.midterm22b1num0027.data.WordDatabase
import kotlinx.coroutines.flow.Flow

class WordRepository(context: Context) {
    private val wordDao: WordDao = WordDatabase.Companion.getDatabase(context).wordDao()
    val words: Flow<List<Word>> = wordDao.getAllWords()
    suspend fun insertWord(word: Word) = wordDao.insertWord(word)
    suspend fun updateWord(word: Word) = wordDao.updateWord(word)
    suspend fun deleteWord(word: Word) = wordDao.deleteWord(word)
}