package ru.asshands.softwire.androidacademy2020.persistency

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.persistency.entity.MovieEntity

class PersistencyRepositoryImpl (applicationContext: Context):PersistencyRepository{
    private val moviesDb: PersistencyAppDatabase = PersistencyAppDatabase.create(applicationContext)

    override suspend fun getAll(): List<Movie> = withContext(Dispatchers.IO) {
        moviesDb.nowPlayingMovieDao.getAll().map { toMovie(it) }
    }

    override suspend fun addNew(movie: List<Movie>) = withContext(Dispatchers.IO) {

        moviesDb.nowPlayingMovieDao.insert(toEntity(movie))
    }
    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        moviesDb.nowPlayingMovieDao.clearAll()
    }

    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        moviesDb.nowPlayingMovieDao.deleteById(id)
    }

    private fun toEntity(movie: List<Movie>): List<MovieEntity> =
        movie.map {MovieEntity(movie = it)}

    private fun toMovie(entity: MovieEntity) = Movie(
        movieId = entity.movie.movieId,
        title = entity.movie.title,
        overview = entity.movie.overview,
        poster = entity.movie.poster,
        backdrop = entity.movie.backdrop,
        ratings = entity.movie.ratings,
        numberOfRatings = entity.movie.numberOfRatings,
        minimumAge = entity.movie.minimumAge,
        runtime = entity.movie.runtime,
        genres = entity.movie.genres,
        actors = entity.movie.actors
    )

}