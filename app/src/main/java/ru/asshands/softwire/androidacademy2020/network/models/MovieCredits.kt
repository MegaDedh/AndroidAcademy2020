package ru.asshands.softwire.androidacademy2020.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieCredits(
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Crew>
)

@Serializable
data class Cast(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,

    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("cast_id")
    val castID: Int,
    val character: String,
    @SerialName("credit_id")
    val creditID: String,
    val order: Long? = null,
)

@Serializable
data class Crew(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("credit_id")
    val creditID: String,
    val department: String,
    val job: String
)