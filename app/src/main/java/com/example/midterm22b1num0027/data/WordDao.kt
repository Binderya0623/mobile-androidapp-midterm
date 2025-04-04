package com.example.midterm22b1num0027.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)
    @Update suspend fun updateWord(word: Word)
    @Delete suspend fun deleteWord(word: Word)
}
