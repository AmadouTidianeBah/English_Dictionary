@file:OptIn(ExperimentalMaterial3Api::class)

package com.atb.englishdictionary.presentation.dictionary_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atb.englishdictionary.domain.model.Word
import com.atb.englishdictionary.core.presentation.DictionaryWordItem

@Composable
fun DictionaryHomeSearch(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
    onCloseSearchClick: () -> Unit,
    words: List<Word>,
    isLoading: Boolean
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = searchActive,
        onActiveChange = onSearchActiveChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchActive) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "close search",
                    modifier = Modifier
                        .clickable {
                            onCloseSearchClick()
                        }
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                items(words) {word ->
                    DictionaryWordItem(
                        word = word
                    )
                    Divider()
                }
            }

            if (isLoading && words.isEmpty()) {
                CircularProgressIndicator()
            }
        }
    }
}