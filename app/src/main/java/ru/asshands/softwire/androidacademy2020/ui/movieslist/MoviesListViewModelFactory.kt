package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.asshands.softwire.androidacademy2020.persistency.PersistencyRepositoryImpl

class MoviesListViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java ->
            MoviesListViewModel(PersistencyRepositoryImpl(applicationContext))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}