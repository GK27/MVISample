package com.example.mvisample.domain.state

import com.example.mvisample.data.model.Search

sealed class MovieState {
    object Loading : MovieState()
    data class Success(val movies: List<Search>, val page: Int, val hasMore: Boolean) : MovieState()
    data class Error(val message: String) : MovieState()
}