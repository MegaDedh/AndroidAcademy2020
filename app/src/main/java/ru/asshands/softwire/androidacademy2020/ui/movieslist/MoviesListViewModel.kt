package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.network.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.network.models.NowPlaying
import ru.asshands.softwire.androidacademy2020.persistency.PersistencyRepository
import ru.asshands.softwire.androidacademy2020.network.services.RetrofitModule
import ru.asshands.softwire.androidacademy2020.utils.buildMoviesList
import ru.asshands.softwire.androidacademy2020.utils.toLog
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MoviesListViewModel(
    private val persistencyRepository: PersistencyRepository,
) : ViewModel() {

    private var genresMap = mapOf<Int, String>()
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            "MoviesListViewModel",
            "Coroutine exception, scope active:${coroutineScope.isActive}",
            throwable
        )
    }

    private val mutableMovieDbConfig = MutableLiveData<MovieDbConfig>()
    val movieDbConfig: LiveData<MovieDbConfig> get() = mutableMovieDbConfig

    private val mutableNowPlaying = MutableLiveData<List<Movie>>(emptyList())
    val nowPlaying: LiveData<List<Movie>> get() = mutableNowPlaying

    private val mutableFavoriteMovies = MutableLiveData<Map<Int, Boolean>>()
    val favoriteMovies: MutableLiveData<Map<Int, Boolean>> get() = mutableFavoriteMovies


    fun loadNowPlaying() {

        coroutineScope.launch {
            Log.d("XXXX", "Start")
            mutableNowPlaying.value = dbGetAll()
            Log.d("XXXX", "dbGetAll")
            loadMovieDbConfig()
            Log.d("XXXX", "loadMovieDbConfig")
            loadGenresMap()
            Log.d("XXXX", "loadGenresMap")

            if (mutableMovieDbConfig.value == null) loadMovieDbConfig()
            val nowPlaying = loadNowPlayingRaw()

            val moviesList =
                mutableMovieDbConfig.value?.let { buildMoviesList(nowPlaying, genresMap, it) }

            if (moviesList !== null) {
                mutableNowPlaying.value = moviesList
                Log.d("XXXX", "loadNowPlayingRaw and setFreshMovies")
                dbClearAll()
                Log.d("XXXX", "dbClearAll")
                dbAddList(moviesList)
                Log.d("XXXX", "dbAddList")

                // TODO лучше записывать все когда-либо загруженые фильмы в БД
                // а в списках Now Playing, Latest, Popular,Top Rated, Upcoming хранить "movieId"
                // затем получать нужные набор из БД по этому ключу
            }
        }
    }

    private suspend fun loadMovieDbConfig() {
        delay(2000) // HardWork
        mutableMovieDbConfig.value = RetrofitModule.movieDbApi.getMovieDbConfig()
    }

    private suspend fun dbGetAll(): List<Movie> {
        delay(2000) // HardWork
        val movies = persistencyRepository.getAll()
        toLog("XXX-All", movies)
        return movies
    }

    private suspend fun dbAddList(movies: List<Movie>) {
        delay(2000) // HardWork
        persistencyRepository.addNew(movies)
    }

    private suspend fun dbClearAll() {
        delay(2000) // HardWork
        persistencyRepository.clearAll()
    }


    private suspend fun loadNowPlayingRaw(): NowPlaying {
        delay(2000) // HardWork
        return suspendCoroutine { continuation ->
            coroutineScope.launch {
                continuation.resume(RetrofitModule.movieDbApi.getNowPlaying())
            }
        }
    }

    private suspend fun loadGenresMap() {
        delay(2000) // HardWork
        genresMap = RetrofitModule
            .movieDbApi
            .getMovieGenres()
            .genres
            .map { it.id to it.name }
            .toMap()
    }

/*    private fun convertMoviesList(nowPlaying: NowPlaying): List<Movie> {
        val moviesList = mutableListOf<Movie>()

        nowPlaying.results.forEach {
            val genresList = mutableListOf<Genre>()
            it.genreIDS.forEach { id ->
                val genreValue = genresMap.getValue(id.toInt())
                genresList.add(Genre(id.toInt(), genreValue))
            }
            moviesList
                .add(
                    Movie(
                        movieId = it.id,
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
                        actors = emptyList()
                    )
                )
        }
        return moviesList
    }*/

//    //TODO перенести "избранное" из MoviesDetailsActorAdapter
//    fun saveFavorite(movieId: Int, favorite: Boolean) {
//        val ed = sharedPreferences.edit()
//        ed.putBoolean(movieId.toString(), favorite)
//        ed.apply()
//        Log.d("DEBUG-TAG", "id=$movieId favorite=$favorite saved")
//    }
//
//    fun loadFavorite(movieId: Int) {
//      favoriteMovies.value = mapOf(1 to true)
//      sharedPreferences.getBoolean(movieId.toString(), false)
//    }

}