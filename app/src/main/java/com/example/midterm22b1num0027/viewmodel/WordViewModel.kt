package com.example.midterm22b1num0027.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm22b1num0027.data.Word
import com.example.midterm22b1num0027.repository.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    //shared property
    var editingWord: Word? = null
    val words = repository.words.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun addWord(word: Word) {viewModelScope.launch { repository.insertWord(word)}}
    fun updateWord(word: Word) {viewModelScope.launch { repository.updateWord(word)}}
    fun deleteWord(word: Word) {viewModelScope.launch { repository.deleteWord(word)}}
}
