package ru.asshands.softwire.androidacademy2020.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.asshands.softwire.androidacademy2020.R

fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}