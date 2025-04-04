@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvisample.presentation.ui.MovieScreen
import com.example.mvisample.presentation.ui.theme.MVISampleTheme
import com.example.mvisample.presentation.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = MovieViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = { SimpleHeader(title = "Movie App") }) {
                Column(modifier = Modifier.padding(it)) {
                    MovieScreen(viewModel)
                }
            }

        }
    }
}


@Composable
fun SimpleHeader(title: String) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
    )
}