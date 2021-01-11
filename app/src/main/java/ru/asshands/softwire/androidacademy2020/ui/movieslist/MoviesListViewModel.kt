package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.models.MovieCredits
import ru.asshands.softwire.androidacademy2020.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.models.MovieDetails
import ru.asshands.softwire.androidacademy2020.models.NowPlaying
import ru.asshands.softwire.androidacademy2020.services.RetrofitModule
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MoviesListViewModel : ViewModel() {

    private var genresMap = mapOf<Int, String>()
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            "MoviesListViewModel",
            "Coroutine exception, scope active:${coroutineScope.isActive}",
            throwable
        )
    }


//    private val mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
//    val moviesList: LiveData<List<Movie>> get() = mutableMoviesList

    private val mutableMovieDbConfig = MutableLiveData<MovieDbConfig>()
    val movieDbConfig: LiveData<MovieDbConfig> get() = mutableMovieDbConfig

    private val mutableNowPlaying = MutableLiveData<List<Movie>>(emptyList())
    val nowPlaying: LiveData<List<Movie>> get() = mutableNowPlaying

    private val mutableFavoriteMovies = MutableLiveData<Map<Int, Boolean>>()
    val favoriteMovies: MutableLiveData<Map<Int, Boolean>> get() = mutableFavoriteMovies


/*    fun loadMoviesList(ctx: Context) {
        // загрузка из assets
        CoroutineScope(Dispatchers.Main).launch {
            // пока передаём контекст в учебных целях
            mutableMoviesList.value = loadMovies(ctx)
        }
    }*/

    fun loadMovieDbConfig() {
        coroutineScope.launch {
            mutableMovieDbConfig.value =
                RetrofitModule.movieDbApi.getMovieDbConfig()
        }
    }

    fun loadNowPlaying() {
        loadGenresMap()
        if (mutableMovieDbConfig.value == null) loadMovieDbConfig()

        coroutineScope.launch {
            val nowPlaying = loadNowPlayingRaw()
            mutableNowPlaying.value = convertMoviesList(nowPlaying)
        }
    }

    private suspend fun loadNowPlayingRaw(): NowPlaying {
        return suspendCoroutine { continuation ->
            coroutineScope.launch {
                continuation.resume(RetrofitModule.movieDbApi.getNowPlaying())
            }
        }
    }

    private fun loadGenresMap() {
        coroutineScope.launch {
            genresMap =
                RetrofitModule.movieDbApi.getMovieGenres().genres.map { it.id to it.name }.toMap()
        }
    }

    private fun convertMoviesList(nowPlaying: NowPlaying): List<Movie> {
        val moviesList = mutableListOf<Movie>()

        nowPlaying.results.forEach {
            val genresList = mutableListOf<Genre>()
            it.genreIDS.forEach { id ->
                val genreValue = genresMap.getValue (id.toInt())
                genresList.add(Genre(id.toInt(), genreValue))
            }
            moviesList
                .add(
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        poster = "${mutableMovieDbConfig.value?.images?.secureBaseURL}${
                            mutableMovieDbConfig.value?.images?.posterSizes?.get(5)
                        }${it.posterPath}",
                        backdrop = "${mutableMovieDbConfig.value?.images?.secureBaseURL}${
                            mutableMovieDbConfig.value?.images?.backdropSizes?.get(1)
                        }${it.backdropPath}",
                        ratings = it.voteAverage,
                        numberOfRatings = it.voteCount,
                        minimumAge = if (it.adult) 18 else 0,
                        runtime = 0,
                        genres = genresList,
                        actors = listOf(Actor(0, "ActorName", "ActorPhoto"))
                    )
                )
        }
        return moviesList
    }

    //TODO перенести "избранное" из MoviesDetailsActorAdapter
    fun saveFavorite(movieId: Int, favorite: Boolean) {
        TODO()
//        val ed = sharedPreferences.edit()
//        ed.putBoolean(movieId.toString(), favorite)
//        ed.apply()
//        Log.d("DEBUG-TAG", "id=$movieId favorite=$favorite saved")
    }

    fun loadFavorite(movieId: Int) {
        TODO()
//      favoriteMovies.value = mapOf(1 to true)
//      sharedPreferences.getBoolean(movieId.toString(), false)
    }
}