package ru.asshands.softwire.androidacademy2020.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.asshands.softwire.androidacademy2020.data.Movie

class MovieDetailsViewModel : ViewModel() {

    private val mutableMovie = MutableLiveData<Movie>()
    val moviesList: LiveData<Movie> get() = mutableMovie

    fun setMovie(movie: Movie) {
        mutableMovie.value = movie
    }
}