package com.atb.englishdictionary.presentation.dictionary_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atb.englishdictionary.core.Resource
import com.atb.englishdictionary.domain.use_case.DictionaryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryWordViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases
): ViewModel() {
    private var _wordState = MutableStateFlow(DictionaryWordState())
    val wordState: StateFlow<DictionaryWordState> = _wordState
    private var _query = mutableStateOf("")
    val query: State<String> = _query
    private var job: Job? = null
    private var _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    init {
        viewModelScope.launch {
            dictionaryUseCases.getAllWords().also {words ->
                _wordState.update {
                    it.copy(history = words.distinct())
                }
            }
        }
    }

    fun updateQuery() {
        _query.value = ""
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            dictionaryUseCases.apply {
                deleteAllWords()
                getAllWords().also {words ->
                    _wordState.update {
                        it.copy(history = words.distinct())
                    }
                }
            }
        }
    }

    fun updateWords() {
        _wordState.update {
            it.copy(words = emptyList())
        }
    }

    fun onSearch(query: String) {
        _query.value = query
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            dictionaryUseCases.getWords(query).onEach {result ->
                when(result) {
                    is Resource.Loading -> {
                        _wordState.update {
                            it.copy(words = result.data ?: emptyList(), isLoading = true)
                        }
                    }
                    is Resource.Error -> {
                        _wordState.update {
                            it.copy(words = result.data ?: emptyList(), isLoading = false)
                        }
                        _uiEvent.emit(UiEvent.ShowSnackBar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Success -> {
                        dictionaryUseCases.getAllWords().also {words ->
                            _wordState.update {
                                it.copy(
                                    words = result.data ?: emptyList(),
                                    isLoading = false,
                                    history = words.distinct()
                                )
                            }
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
    }
}