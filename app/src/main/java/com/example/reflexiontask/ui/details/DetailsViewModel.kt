package com.example.reflexiontask.ui.details

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
class DetailsViewModel @Inject constructor(private val dao: MovieDAO) : ViewModel() {

    // to update image state
    private var _existInDB = MutableLiveData<Boolean>()
    val existInDB: LiveData<Boolean>
        get() = _existInDB

    // to hold current movie
    lateinit var currentMovie: Movie


    // check if the anime already saved inside the Database
    fun checkIfAlreadyExist() {
        viewModelScope.launch {
            _existInDB.value = dao.getAllMovies().any { it.IMDBID == currentMovie.IMDBID }
        }
    }

    // save/Delete movie from Database
    fun saveToDB() {
        viewModelScope.launch {
            if (_existInDB.value == true) {
                dao.delete(currentMovie)
            } else {
                dao.insert(currentMovie)
            }
            checkIfAlreadyExist()
        }
    }
}