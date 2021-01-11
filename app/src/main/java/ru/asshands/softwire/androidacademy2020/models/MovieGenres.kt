package ru.asshands.softwire.androidacademy2020.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenres(
    @SerialName("genres")
    val genres: List<GenreDb>,

)

@Serializable
data class GenreDb(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)