package ru.asshands.softwire.androidacademy2020.persistency

import ru.asshands.softwire.androidacademy2020.data.Movie

interface PersistencyRepository {

    suspend fun getAll(): List<Movie>

    suspend fun addNew(movie: List<Movie>)

    suspend fun clearAll()

    suspend fun deleteById(id: Long)

}