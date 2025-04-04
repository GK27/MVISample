package com.example.mvisample.data.remote

import com.example.mvisample.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("?")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("page") page: Int
    ): MovieResponse
}