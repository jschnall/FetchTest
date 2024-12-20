package com.example.fetch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fetch.ui.theme.FetchTestTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchTestTheme {
                KoinAndroidContext {
                    val viewModel: FetchListViewModel = koinViewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    FetchListScreen(uiState)
                }
            }
        }
    }
}