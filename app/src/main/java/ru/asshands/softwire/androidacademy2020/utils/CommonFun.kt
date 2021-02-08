package ru.asshands.softwire.androidacademy2020.utils

import android.util.Log

fun <T> toLog(tag: String, collection:Collection<T>) {
    collection.forEach {
        Log.d(tag,it.toString())
    }
}