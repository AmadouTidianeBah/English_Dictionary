package com.atb.englishdictionary.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atb.englishdictionary.domain.model.Word

@Composable
fun DictionaryWordItem(
    modifier: Modifier = Modifier,
    word: Word
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = word.word.uppercase(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = word.phonetic ?: "Phonetic not available",
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(16.dp))

        word.meanings.forEach {meaning ->
            Text(
                text = meaning.partOfSpeech.uppercase(),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            meaning.definitions.forEachIndexed { index, definition ->
                Text(text = "${index + 1}. ${definition.definition}")

                Spacer(modifier = Modifier.height(8.dp))

                definition.example?.let {
                    Text(text = "Example: $it")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}