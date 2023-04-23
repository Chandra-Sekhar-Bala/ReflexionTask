package com.example.reflexiontask.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reflexiontask.model.Movie
import com.example.reflexiontask.repo.network.MovieAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: MovieAPI) : ViewModel() {

    private var _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList
    private var page = 1

    init {
        getMovie(page)
    }

    private fun getMovie(page: Int) {
        // because I have only fixed two page data
        if (page > 2) return

        viewModelScope.launch {

            try {
                val data = api.getMovies(page)
                if (_movieList.value == null) {
                    _movieList.postValue(data.movieList)
                } else {
                    _movieList.postValue(_movieList.value?.plus(data.movieList))
                }
            } catch (e: Exception) {
                Log.i("TAG-TAG", "getMovie:  ${e.message}")
            }

        }
    }

    fun getNextPage() {
        getMovie(++page)
    }

}