package ru.asshands.softwire.androidacademy2020.ui.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.network.models.Cast
import ru.asshands.softwire.androidacademy2020.network.models.MovieCredits
import ru.asshands.softwire.androidacademy2020.network.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.network.models.MovieDetails
import ru.asshands.softwire.androidacademy2020.network.services.RetrofitModule
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MovieDetailsViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            "MoviesListViewModel",
            "Coroutine exception, scope active:${coroutineScope.isActive}",
            throwable
        )
    }

    private val mutableMovie = MutableLiveData<Movie>()
    private val mutableMovieDbConfig = MutableLiveData<MovieDbConfig>()

    private var movieId = 0

    private val mutableMovieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> get() = mutableMovieDetails

    private val mutableMovieCredits = MutableLiveData<MovieCredits>()
    val movieCredits: LiveData<MovieCredits> get() = mutableMovieCredits


    fun setMovie(value: Movie) {
        mutableMovie.value = value
        movieId = value.movieId.toInt()
    }

    fun setMovieDbConfig(value: MovieDbConfig) {
        mutableMovieDbConfig.value = value
    }

    fun loadMovieDetails() {
        coroutineScope.launch {
            // TODO логика такая-же как и в MoviesList
            // TODO пытаемся найти и загрузить Movie из БД по "movieId"
            // TODO отправляем запрос в сеть
            // TODO если пришли валидные network-данные - показываем показываем их:
            val movieDetails = loadMovieDetailsRaw()
            val movieCredits = loadMovieCreditsRaw()
            val movieActors = convertCastToActors(movieCredits.cast)
            mutableMovieDetails.value = movieDetails?.let { convertMoviesDetails(it, movieActors) }
            // TODO добавляем (или перезаписываем) в БД полученый фильм по его "movieId"
        }
    }

    private fun convertCastToActors(cast: List<Cast>): List<Actor> {

        val actors = mutableListOf<Actor>()
        cast.forEach {
            val actorPhoto = "${mutableMovieDbConfig.value?.images?.secureBaseURL}" +
                    "${mutableMovieDbConfig.value?.images?.profileSizes?.get(1)}" +
                    "${it.profilePath}"
            actors.add(Actor(it.id, it.name, actorPhoto))
        }
        return actors
    }

    private fun convertMoviesDetails(movie: MovieDetails, actor: List<Actor>): Movie {
        return Movie(
            movieId = movie.id,
            title = movie.title,
            overview = movie.overview,
            poster = "${mutableMovieDbConfig.value?.images?.secureBaseURL}${
                mutableMovieDbConfig.value?.images?.posterSizes?.get(5)
            }${movie.posterPath}",
            backdrop = "${mutableMovieDbConfig.value?.images?.secureBaseURL}${
                mutableMovieDbConfig.value?.images?.backdropSizes?.get(1)
            }${movie.backdropPath}",
            ratings = movie.voteAverage,
            numberOfRatings = movie.voteCount,
            minimumAge = if (movie.adult) 18 else 0,
            runtime = 0,
            genres = movie.genres.map { Genre(id = it.id.toInt(), name = it.name) },
            actors = actor
        )
    }

    private suspend fun loadMovieDetailsRaw(): MovieDetails? {
        return suspendCoroutine { continuation ->
            coroutineScope.launch {
                continuation.resume(RetrofitModule.movieDbApi.getMovieDetails(movieId))
            }
        }
    }

    private suspend fun loadMovieCreditsRaw(): MovieCredits {
        return suspendCoroutine { continuation ->
            coroutineScope.launch {
                continuation.resume(RetrofitModule.movieDbApi.getMovieCredits(movieId))
            }
        }
    }

}