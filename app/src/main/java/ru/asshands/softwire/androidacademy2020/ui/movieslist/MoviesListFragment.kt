package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.work.WorkManager
import ru.asshands.softwire.androidacademy2020.ui.moviedetails.MovieDetailsFragment
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMoviesListBinding
import ru.asshands.softwire.androidacademy2020.network.models.MovieDbConfig
import ru.asshands.softwire.androidacademy2020.ui.moviedetails.MovieDetailsViewModel


class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(requireContext().applicationContext)
    }
    private val viewModelMovieDetails: MovieDetailsViewModel by activityViewModels()

    private val workRepository = WorkRepository()
    private var _bind: FragmentMoviesListBinding? = null
    private val bind get() = _bind!!
    private lateinit var adapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _bind = FragmentMoviesListBinding.bind(view)
        initMoviesListAdapter()
        viewModel.movieDbConfig.observe(this.viewLifecycleOwner, this::getMovieDbConfig)
        viewModel.nowPlaying.observe(this.viewLifecycleOwner, this::getNowPlaying)

        WorkManager.getInstance(requireContext()).enqueue(workRepository.constrainedRequest)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadNowPlaying()
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

    private fun getMovieDbConfig(movieDbConfig: MovieDbConfig) {
        Log.d("REZ-MovieDbConfig", movieDbConfig.toString())
        viewModelMovieDetails.setMovieDbConfig(movieDbConfig)
    }

    private fun getNowPlaying(nowPlaying: List<Movie>) {
        Log.d("REZ-NowPlaying", nowPlaying.toString())
        setMoviesListAdapter(nowPlaying)
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