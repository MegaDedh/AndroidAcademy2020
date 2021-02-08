package ru.asshands.softwire.androidacademy2020.persistency

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.asshands.softwire.androidacademy2020.persistency.dao.NowPlayingMovieDao
import ru.asshands.softwire.androidacademy2020.persistency.entity.MovieEntity

//@Database(entities = [MovieEntity::class, MovieInfoEntity::class], version = 1)
@Database(entities = [MovieEntity::class], version = 4)
@TypeConverters(value = [TConverters::class])

abstract class PersistencyAppDatabase: RoomDatabase() {

    abstract val nowPlayingMovieDao: NowPlayingMovieDao
    //abstract val movieInfoDao: MovieInfoDao

    companion object {

        fun create(context: Context): PersistencyAppDatabase =
            Room.databaseBuilder(context, PersistencyAppDatabase::class.java, DbContract.DATABASE_NAME)
                .fallbackToDestructiveMigration() // Пересоздание базы данных вместо миграции. Но номер версии всё равно нужно менять
                .build()

    }
}