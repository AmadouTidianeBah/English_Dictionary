package com.atb.englishdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atb.englishdictionary.presentation.dictionary_home.DictionaryHomeScreen
import com.atb.englishdictionary.presentation.dictionary_home.DictionaryWordViewModel
import com.atb.englishdictionary.presentation.ui.theme.EnglishDictionaryTheme
import com.atb.englishdictionary.presentation.word_detail.WordDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishDictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "dictionary_home"
                    ) {
                        composable("dictionary_home") {
                            DictionaryHomeScreen(
                                navigateToWordDetail = {word ->
                                    navController
                                        .navigate("word_detail/$word")
                                },
                                onSearchClick = {word ->
                                    navController
                                        .navigate("word_detail/$word")
                                }
                            )
                        }

                        composable("word_detail/{wordId}") {
                            WordDetailScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EnglishDictionaryTheme {
        Greeting("Android")
    }
}