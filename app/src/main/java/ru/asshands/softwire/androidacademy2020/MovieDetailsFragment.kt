package ru.asshands.softwire.androidacademy2020

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.asshands.softwire.androidacademy2020.adapters.MoviesDetailsActorAdapter
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMovieDetailsBinding
import ru.asshands.softwire.androidacademy2020.domain.MoviesDataSource
import ru.asshands.softwire.androidacademy2020.models.Actor
import ru.asshands.softwire.androidacademy2020.models.Movie

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {

        fun newInstance(movieId: Long) = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong("MOVIE_ID", movieId)
            }
        }
    }

    private var _bind: FragmentMovieDetailsBinding? = null
    private val bind get() = _bind!!
    private var movieId: Long = 0
    private var movie: Movie? = null
    private lateinit var adapter: MoviesDetailsActorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMovieDetailsBinding.bind(view)

        movieId = requireArguments().getLong("MOVIE_ID")
        val moviesList = MoviesDataSource().getMovies()
        movie = moviesList.firstOrNull { it.id == movieId }

        // (!) callback way
        adapter = MoviesDetailsActorAdapter {
            showSnackBarActor(it)
        }
        val recyclerView = bind.fragmentMovieDetailsActorRecycler
        recyclerView.adapter = adapter

        val reviewsTemplate = requireContext().getString(R.string.reviews, movie?.reviews)
        movie?.rating?.let { setRating(bind, it) }

        bind.apply {
            reviews.text = reviewsTemplate
            fragmentMovieDetailsAgeRating.text = movie?.ageRating
            fragmentMovieDetailsTitle.text = movie?.title
            fragmentMovieDetailsTitleGenreTags.text = movie?.genreTags
            storyLine.text = movie?.storyline
            fragmentMovieDetailsBackBackText.setOnClickListener {
                requireActivity().supportFragmentManager
                    .popBackStack()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        movie?.actors?.let { adapter.bindData(it) }
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }

    private fun setRating(bind: FragmentMovieDetailsBinding, rating: Int) {
        val star1 = bind.star1
        val star2 = bind.star2
        val star3 = bind.star3
        val star4 = bind.star4
        val star5 = bind.star5

        if (rating >= 1) star1.setImageResource(R.drawable.star_icon_red)
        if (rating >= 2) star2.setImageResource(R.drawable.star_icon_red)
        if (rating >= 3) star3.setImageResource(R.drawable.star_icon_red)
        if (rating >= 4) star4.setImageResource(R.drawable.star_icon_red)
        if (rating >= 5) star5.setImageResource(R.drawable.star_icon_red)
    }

    private fun showSnackBarActor(actor: Actor) {
        Snackbar.make(
            bind.fragmentMovieDetailsActorRecycler,
            actor.name,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}