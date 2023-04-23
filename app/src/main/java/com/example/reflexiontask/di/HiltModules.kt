package com.example.reflexiontask.di

import android.content.Context
import androidx.room.Room
import com.example.reflexiontask.Constants
import com.example.reflexiontask.repo.db.MovieDAO
import com.example.reflexiontask.repo.db.MovieDatabase
import com.example.reflexiontask.repo.network.MovieAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModules {

    @Singleton
    @Provides
    fun getRetrofit () : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun getMovieAPI(retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "Movies")
            .build()
    }

    @Singleton
    @Provides
    fun getDAO(database: MovieDatabase) : MovieDAO{
        return database.getDao()
    }

}