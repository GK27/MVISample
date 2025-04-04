package com.example.mvisample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvisample.data.repository.MovieRepository
import com.example.mvisample.domain.intent.MovieIntent
import com.example.mvisample.domain.state.MovieState
import com.example.mvisample.util.common.Constant.SEARCH_QUERY
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val _uiState = MutableStateFlow<MovieState>(MovieState.Loading)
    val uiState: StateFlow<MovieState> = _uiState.asStateFlow()

    private val _intentFlow = MutableSharedFlow<MovieIntent>()

    private var currentPage = 1
    private var totalResults = 0

    init {
        processIntents()
    }

    fun sendIntent(intent: MovieIntent) {
        viewModelScope.launch {
            _intentFlow.emit(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            _intentFlow.collect { intent ->
                when (intent) {
                    is MovieIntent.LoadMovies -> fetchMovies(intent.query, 1)
                    is MovieIntent.LoadMore -> fetchNextPage()
                }
            }
        }
    }

    private fun fetchMovies(query: String, page: Int) {
        viewModelScope.launch {
            _uiState.value = MovieState.Loading
            try {
                val movies = repository.fetchMovies(query, page)
                val hasMore = movies.isNotEmpty()
                currentPage = page
                _uiState.value = MovieState.Success(movies, currentPage, hasMore)
            } catch (e: Exception) {
                _uiState.value = MovieState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchNextPage() {
        fetchMovies(SEARCH_QUERY, currentPage + 1)
    }
}