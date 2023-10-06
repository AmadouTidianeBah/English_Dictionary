package com.atb.englishdictionary.presentation.dictionary_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.atb.englishdictionary.presentation.dictionary_home.components.DictionaryHistoryItem
import com.atb.englishdictionary.presentation.dictionary_home.components.DictionaryHomeSearch
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DictionaryHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryWordViewModel = hiltViewModel(),
    navigateToWordDetail: (String) -> Unit,
    onSearchClick: (String) -> Unit
) {
    val state = viewModel.wordState.collectAsState()
    val query = viewModel.query.value
    val uiEvent = viewModel.uiEvent
    var searchActive by rememberSaveable {
        mutableStateOf(false)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest {event ->
            when(event) {
                is DictionaryWordViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState)}
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DictionaryHomeSearch(
                query = query,
                onQueryChange = viewModel::onSearch,
                onSearch = {word ->
                    if (word in state.value.history) {
                        onSearchClick(word)
                        searchActive = false
                        viewModel.apply {
                            updateWords()
                            updateQuery()
                        }
                    }
                },
                searchActive = searchActive,
                onSearchActiveChange = { searchActive = !searchActive },
                onCloseSearchClick = {
                    if (query.isBlank()) {
                        searchActive = false
                        viewModel.updateWords()
                    }
                    else viewModel.updateQuery()
                },
                words = state.value.words,
                isLoading = state.value.isLoading
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "History",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = {
                    viewModel.deleteAllWords()
                }) {
                    Text(
                        text = "Clear",
                        fontStyle = FontStyle.Italic,
                        color = Color.Red
                    )
                }
            }
            if (state.value.history.isEmpty()) {
                Box(modifier = Modifier.fillMaxHeight()) {
                    Text(text = "Empty", fontSize = 24.sp)
                }
            } else {
                LazyColumn {
                    items(state.value.history) {history ->
                        DictionaryHistoryItem(
                            history = history.uppercase(),
                            onHistoryItemClick = {word ->
                                navigateToWordDetail(word)
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}