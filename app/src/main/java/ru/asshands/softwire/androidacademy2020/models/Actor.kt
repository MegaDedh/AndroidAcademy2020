package ru.asshands.softwire.androidacademy2020.models

import android.graphics.drawable.Drawable

data class Actor(
    val id: Long,
    val name: String,
    val surname: String,
    val posterUrl: String,
    val posterResource: Drawable?,
)