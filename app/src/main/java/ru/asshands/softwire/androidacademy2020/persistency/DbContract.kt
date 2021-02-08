package ru.asshands.softwire.androidacademy2020.persistency

import android.provider.BaseColumns

object DbContract {

    const val DATABASE_NAME = "Movies.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_MOVIE_ID = "index"
        const val COLUMN_NAME_MOVIE_DATA = "movie"


        /*
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_LATITUDE = "latitude"
        const val COLUMN_NAME_LONGITUDE = "longitude"
        */
    }
}