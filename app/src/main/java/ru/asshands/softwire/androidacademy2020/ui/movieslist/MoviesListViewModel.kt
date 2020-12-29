package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.data.loadMovies

class MoviesListViewModel : ViewModel() {

    private val mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> get() = mutableMoviesList

    private val mutableFavoriteMovies = MutableLiveData<Map<Int, Boolean>>()
    val favoriteMovies: MutableLiveData<Map<Int, Boolean>> get() = mutableFavoriteMovies

    fun loadMoviesList(ctx: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            // пока передаём контекст в учебных целях
            mutableMoviesList.value = loadMovies(ctx)
        }
    }


    //TODO перенести "избранное" из MoviesDetailsActorAdapter
    fun saveFavorite(movieId: Int, favorite: Boolean) {
        TODO()
//        val ed = sharedPreferences.edit()
//        ed.putBoolean(movieId.toString(), favorite)
//        ed.apply()
//        Log.d("DEBUG-TAG", "id=$movieId favorite=$favorite saved")
    }

    fun loadFavorite(movieId: Int){
        TODO()
//      favoriteMovies.value = mapOf(1 to true)
//      sharedPreferences.getBoolean(movieId.toString(), false)
    }
}