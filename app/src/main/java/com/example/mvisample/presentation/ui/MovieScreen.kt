package com.example.mvisample.presentation.ui

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvisample.data.model.Search
import com.example.mvisample.domain.intent.MovieIntent
import com.example.mvisample.domain.state.MovieState
import com.example.mvisample.presentation.ui.theme.MVISampleTheme
import com.example.mvisample.presentation.viewmodel.MovieViewModel


@Composable
fun MovieScreen(viewModel: MovieViewModel) {
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("Batman") }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MovieIntent.LoadMovies(query))
    }

    when (state) {
        is MovieState.Loading -> CircularProgressIndicator()
        is MovieState.Success -> {
            val movieState = state as MovieState.Success
            MovieList(movieState.movies, movieState.hasMore) {
                viewModel.sendIntent(MovieIntent.LoadMore)
            }
        }

        is MovieState.Error -> Text("Error: ${(state as MovieState.Error).message}")
    }
}

@Composable
fun MovieList(movies: List<Search>, hasMore: Boolean, loadMore: () -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie)
        }
        if (hasMore) {
            item {
                CircularProgressIndicator()
                LaunchedEffect(Unit) {
                    loadMore()
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Search) {
    Row(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = movie.Poster,
            contentDescription = movie.Title,
            modifier = Modifier.size(100.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = movie.Title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Year: ${movie.Year}")
        }
    }
}