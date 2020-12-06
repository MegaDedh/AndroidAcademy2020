package ru.asshands.softwire.androidacademy2020.models

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val posterResource: Int,
    val genreTags: String,
    val rating: Int,
    val reviews: Int,
    val runningTime: Int,
    val ageRating: String,
    val favorite: Boolean,
    val actors: List<Actor>,
    val storyline: String,
)