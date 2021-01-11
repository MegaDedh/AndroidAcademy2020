package ru.asshands.softwire.androidacademy2020.services

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_NAME = "api_key"
private const val API_KEY_VALUE = "f7a9fa8041e38811bbbf68f6914b4321"

object RetrofitModule {
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(MovieDbApiHeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieDbApi: MovieDbApi = retrofit.create()
}

private class MovieDbApiHeaderInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
            //.addPathSegment("api_key=$apiKey") // if need segment to end: /[segment]?
            .build()

        val request = originalRequest.newBuilder()
            .url(newHttpUrl)
            //.addHeader(apiKeyName, apiKeyValue) // if need add Header
            .build()

        return chain.proceed(request)
    }
}