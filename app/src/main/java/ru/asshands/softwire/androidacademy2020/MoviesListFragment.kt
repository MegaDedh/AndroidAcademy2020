package ru.asshands.softwire.androidacademy2020

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.asshands.softwire.androidacademy2020.adapters.MoviesListAdapter
import ru.asshands.softwire.androidacademy2020.adapters.OnRecyclerItemMovieClicked
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMoviesListBinding
import ru.asshands.softwire.androidacademy2020.domain.MoviesDataSource
import ru.asshands.softwire.androidacademy2020.models.Movie

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var _bind: FragmentMoviesListBinding? = null
    private val bind get() = _bind!!
    private lateinit var adapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _bind = FragmentMoviesListBinding.bind(view)
        adapter = MoviesListAdapter(clickListener)
        val recyclerView = bind.fragmentMovieListRecyclerView
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.bindData(MoviesDataSource().getMovies())
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }

    private val clickListener = object : OnRecyclerItemMovieClicked {
        override fun onClick(movie: Movie) {
           val fragment = MovieDetailsFragment.newInstance(movie.id)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}