package ru.asshands.softwire.androidacademy2020.ui.movieslist

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest

class WorkRepository {

    private val constraints =
        Constraints.Builder().setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED).build()

    // setRequiredNetworkType(NetworkType.CONNECTED).build()
    val constrainedRequest = OneTimeWorkRequest.Builder(MovieWorker::class.java)
        .setConstraints(constraints)
        .build()
}