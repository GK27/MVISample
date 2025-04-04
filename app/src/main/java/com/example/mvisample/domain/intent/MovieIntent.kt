package com.example.mvisample.domain.intent


sealed class MovieIntent {
    data class LoadMovies(val query: String) : MovieIntent()
    object LoadMore : MovieIntent()
}