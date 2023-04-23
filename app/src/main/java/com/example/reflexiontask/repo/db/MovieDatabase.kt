package com.example.reflexiontask.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reflexiontask.model.Movie

@Database([Movie::class], version = 1)
abstract class MovieDatabase() : RoomDatabase() {

    abstract fun getDao(): MovieDAO

}