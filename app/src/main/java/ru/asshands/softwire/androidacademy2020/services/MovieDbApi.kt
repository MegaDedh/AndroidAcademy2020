package ru.asshands.softwire.androidacademy2020.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.asshands.softwire.androidacademy2020.models.*

interface MovieDbApi {

    @GET("configuration?")
    suspend fun getMovieDbConfig(): MovieDbConfig

    @GET("genre/movie/list?")
    suspend fun getMovieGenres(): MovieGenres

    @GET("movie/now_playing?")
    suspend fun getNowPlaying(): NowPlaying

    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetails

    @GET("movie/{movie_id}/credits?")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): MovieCredits
}