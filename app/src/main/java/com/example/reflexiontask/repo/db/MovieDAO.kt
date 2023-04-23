package com.example.reflexiontask.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reflexiontask.model.Movie

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM Movies")
    suspend fun getAllMovies() : List<Movie>

}