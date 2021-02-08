package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.network.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.network.models.NowPlaying
import ru.asshands.softwire.androidacademy2020.network.services.RetrofitModule
import ru.asshands.softwire.androidacademy2020.persistency.PersistencyRepositoryImpl
import ru.asshands.softwire.androidacademy2020.utils.buildMoviesList
import ru.asshands.softwire.androidacademy2020.utils.toLog
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MovieWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val persistencyRepository = PersistencyRepositoryImpl(context)
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var movieDbConfig: MovieDbConfig? = null
    private var genresMap: Map<Int, String>? = null
    private var nowPlaying: NowPlaying? = null
    override fun doWork(): Result {
        Log.d("XX-Worker", "fun doWork started")
        var result: Result = Result.failure()

        coroutineScope.launch {
            movieDbConfig = loadMovieDbConfig()
            Log.d("XX-Worker", "loadMovieDbConfig")
            genresMap = loadGenresMap()
            Log.d("XX-Worker", "loadGenresMap")
            nowPlaying = loadNowPlayingRaw()

            result = if (nowPlaying != null || movieDbConfig != null || genresMap != null) {
                val moviesList = buildMoviesList(nowPlaying!!, genresMap!!, movieDbConfig!!)
                //val moviesList = getFakeMoveList() // для проверки

                if (moviesList.isNotEmpty()) {
                    dbClearAll()
                    Log.d("XX-Worker", "dbClearAll")
                    toLog("XX-Worker",moviesList)
                    dbAddList(moviesList)

                    Log.d("XX-Worker", "dbAddList")
                }

                Log.d("XX-Worker", "Result.success()")
                Result.success()

            } else {
                Log.d("XX-Worker", "Result.failure()")
                Result.failure()
            }
        }
        return result
    }

    private suspend fun loadMovieDbConfig(): MovieDbConfig {
        delay(2000) // HardWork
        return RetrofitModule.movieDbApi.getMovieDbConfig()
    }

    private suspend fun loadGenresMap(): Map<Int, String> {
        delay(2000) // HardWork
        return RetrofitModule
            .movieDbApi
            .getMovieGenres()
            .genres
            .map { it.id to it.name }
            .toMap()
    }

    private suspend fun loadNowPlayingRaw(): NowPlaying {
        delay(2000) // HardWork
        return suspendCoroutine { continuation ->
            coroutineScope.launch {
                continuation.resume(RetrofitModule.movieDbApi.getNowPlaying())
                //continuation.resume(RetrofitModule.movieDbApi.getUpcoming())
            }
        }
    }

    private suspend fun dbClearAll() {
        delay(2000) // HardWork
        persistencyRepository.clearAll()
    }

    private suspend fun dbAddList(movies: List<Movie>) {
        delay(2000) // HardWork
        persistencyRepository.addNew(movies)
    }

    private fun getFakeMoveList():List<Movie> =
        listOf(Movie(
            movieId=464052,
            title="Wonder Woman 1984",
            overview="Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
            poster="https://image.tmdb.org/t/p/w780/bTUFtYSjjvmWTPa9oLzJK5qYVqn.jpg",
            backdrop="https://image.tmdb.org/t/p/w780/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
            ratings=7.0,
            numberOfRatings=3400,
            minimumAge=0,
            runtime=0,
            genres= listOf(Genre(id=14, name="Fantasy"),
                Genre(id=28, name="Action"),
                Genre(id=12, name="Adventure")),
            actors= listOf(Actor(1,"q","q"))
        ))

}