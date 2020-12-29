package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import ru.asshands.softwire.androidacademy2020.ui.moviedetails.MovieDetailsFragment
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.adapters.MoviesListAdapter
import ru.asshands.softwire.androidacademy2020.adapters.OnRecyclerItemMovieClicked
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMoviesListBinding
import ru.asshands.softwire.androidacademy2020.ui.moviedetails.MovieDetailsViewModel


class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by viewModels()
    // в воркшопе было: by viewModels{ MoviesListViewModelFactory() } // зачем нужна эта фабрика?

 // private val viewModelMovieDetails: MovieDetailsViewModel by viewModels()
    private val viewModelMovieDetails: MovieDetailsViewModel by activityViewModels()
    // делаем click на фильме
    // загружаем этот фильм в вью-модель viewModelMovieDetails
    // переходим на экран MovieDetailsFragment
    // там подключаемся к этой вью-модели viewModelMovieDetails
    // получаем оттуда фильм, на который нажали
    // верно ли я для этого использовал activityViewModels() ?

    private var _bind: FragmentMoviesListBinding? = null
    private val bind get() = _bind!!
    private lateinit var adapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _bind = FragmentMoviesListBinding.bind(view)
        initMoviesListAdapter()
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::setMoviesListAdapter)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMoviesList(requireContext())
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }

    private fun initMoviesListAdapter() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        adapter = MoviesListAdapter(sharedPreferences, clickListener)
        val recyclerView = bind.fragmentMovieListRecyclerView
        recyclerView.adapter = adapter
    }

    private fun setMoviesListAdapter(moviesList: List<Movie>) {
        adapter.bindData(moviesList)
    }

    private val clickListener = object : OnRecyclerItemMovieClicked {
        override fun onClick(movie: Movie) {
            viewModelMovieDetails.setMovie(movie)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, MovieDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}