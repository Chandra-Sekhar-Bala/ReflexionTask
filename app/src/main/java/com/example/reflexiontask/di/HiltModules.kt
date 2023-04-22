package com.example.reflexiontask.di

import com.example.reflexiontask.Constants
import com.example.reflexiontask.repo.network.MovieAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    fun getMovieAPI(retrofit: Retrofit) : MovieAPI{
        return  retrofit.create(MovieAPI::class.java)
    }

}