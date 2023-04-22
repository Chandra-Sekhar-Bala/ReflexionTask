package com.example.reflexiontask.repo.network

import com.example.reflexiontask.model.MovieData
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("{page}.json")
    suspend fun getMovies(@Path("page") page: Int = 1): MovieData
}