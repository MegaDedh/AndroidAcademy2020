package ru.asshands.softwire.androidacademy2020.persistency.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.asshands.softwire.androidacademy2020.persistency.entity.MovieEntity

@Dao
interface NowPlayingMovieDao {

    @Query("SELECT * FROM movies ORDER BY _id ASC")
    suspend fun getAll(): List<MovieEntity>

    @Insert()
    suspend fun insert(movie: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("DELETE FROM movies WHERE movieId == :id")
    suspend fun deleteById(id: Long)

}