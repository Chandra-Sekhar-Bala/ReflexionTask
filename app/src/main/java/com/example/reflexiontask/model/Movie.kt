package com.example.reflexiontask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity("Movies")
data class Movie(
    val Cast: String = "",
    val Director: String = "",
    val Genres: String = "",
    @PrimaryKey val IMDBID: String = "",
    @SerializedName("Movie Poster")
    val MoviePoster: String = "",
    val Rating: String = "",
    @SerializedName("Runtime")
    val Runtime: String = "",
    @SerializedName("Short Summary")
    val ShortSummary: String = "",
    val Summary: String = "",
    val Title: String = "",
    val Writers: String = "",
    val Year: String = "",
    @SerializedName("YouTube Trailer")
    val YouTubeTrailer: String? = null
): Serializable