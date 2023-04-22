package com.example.reflexiontask.model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("Movie List")
    val movieList: List<Movie> = listOf()
)