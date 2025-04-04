package com.example.mvisample.data.repository

import com.example.mvisample.data.model.Search
import com.example.mvisample.data.remote.MovieApiService
import com.example.mvisample.util.common.Constant.API_KEY
import com.example.mvisample.util.common.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(MovieApiService::class.java)

    suspend fun fetchMovies(query: String, page: Int): List<Search> {
        return api.getMovies(API_KEY, query, page).Search ?: emptyList()
    }
}