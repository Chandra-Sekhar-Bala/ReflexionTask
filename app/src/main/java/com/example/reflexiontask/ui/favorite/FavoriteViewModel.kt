package com.example.reflexiontask.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reflexiontask.model.Movie
import com.example.reflexiontask.repo.db.MovieDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val dao: MovieDAO): ViewModel() {

    private var _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    init {
        getAllMovies()
    }

    fun getAllMovies(){
        viewModelScope.launch {
            _movieList.value = dao.getAllMovies()
        }
    }
}