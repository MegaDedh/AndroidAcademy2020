package ru.asshands.softwire.androidacademy2020.utils

import android.util.Log
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.network.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.network.models.NowPlaying

fun <T> toLog(tag: String, collection:Collection<T>) {
    collection.forEach {
        Log.d(tag,it.toString())
    }
}

fun buildMoviesList(nowPlaying: NowPlaying,genresMap :Map<Int, String>,movieDbConfig: MovieDbConfig): List<Movie> {
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
                    poster = "${movieDbConfig.images?.secureBaseURL}${
                        movieDbConfig.images?.posterSizes?.get(5)
                    }${it.posterPath}",
                    backdrop = "${movieDbConfig.images?.secureBaseURL}${
                        movieDbConfig.images?.backdropSizes?.get(1)
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
}