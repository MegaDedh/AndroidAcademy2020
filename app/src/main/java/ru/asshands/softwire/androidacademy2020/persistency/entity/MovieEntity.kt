package ru.asshands.softwire.androidacademy2020.persistency.entity

import androidx.room.*
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Genre
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.persistency.DbContract

@Entity(
    tableName = DbContract.Movies.TABLE_NAME
    //,indices = [Index(DbContract.Movies.COLUMN_NAME_ID)]
    //,primaryKeys = [DbContract.Movies.COLUMN_NAME_MOVIE_ID] // если хотим использовать id фильма в качестве PK
)
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_ID)
    val pK: Long=0,

  //  @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_MOVIE_DATA)
    @Embedded // Чтобы не раскладывать поля объекта вручную
    val movie: Movie

)