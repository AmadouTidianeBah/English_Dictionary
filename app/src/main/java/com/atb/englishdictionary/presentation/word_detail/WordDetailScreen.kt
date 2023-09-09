package com.atb.englishdictionary.presentation.word_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.atb.englishdictionary.core.presentation.DictionaryWordItem
import com.atb.englishdictionary.domain.model.Word

@Composable
fun WordDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: WordDetailViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(viewModel.wordState.value) {word ->
            DictionaryWordItem(
                word = word,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Divider()
        }
    }
}