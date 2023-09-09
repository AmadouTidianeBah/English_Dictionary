package com.atb.englishdictionary.presentation.word_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atb.englishdictionary.domain.model.Word
import com.atb.englishdictionary.domain.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val repository: DictionaryRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private var _wordState = mutableStateOf(emptyList<Word>())
    val wordState: State<List<Word>> = _wordState


    init {
        savedStateHandle.get<String>("wordId")?.let {word ->
            viewModelScope.launch {
                repository.getWordFromDb(word).also {
                    _wordState.value = it
                }
            }
        }
    }
}