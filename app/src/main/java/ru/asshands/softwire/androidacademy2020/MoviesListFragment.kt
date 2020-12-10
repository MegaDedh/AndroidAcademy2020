package ru.asshands.softwire.androidacademy2020

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.asshands.softwire.androidacademy2020.adapters.MoviesListAdapter
import ru.asshands.softwire.androidacademy2020.adapters.OnRecyclerItemMovieClicked
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.data.loadMovies
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMoviesListBinding


class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var _bind: FragmentMoviesListBinding? = null
    private val bind get() = _bind!!
    private lateinit var adapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        _bind = FragmentMoviesListBinding.bind(view)
        adapter = MoviesListAdapter(sharedPreferences, clickListener)
        val recyclerView = bind.fragmentMovieListRecyclerView
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            val moviesList = loadMovies(requireContext())
            adapter.bindData(moviesList)
        }
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }

    private val clickListener = object : OnRecyclerItemMovieClicked {
        override fun onClick(movie: Movie) {
            val fragment = MovieDetailsFragment.newInstance(movie)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}