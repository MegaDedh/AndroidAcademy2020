package ru.asshands.softwire.androidacademy2020

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.asshands.softwire.androidacademy2020.adapters.MoviesDetailsActorAdapter
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMovieDetailsBinding
import ru.asshands.softwire.androidacademy2020.utils.KEY_MOVIE


class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {

        fun newInstance(movie: Movie) = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_MOVIE, movie)
            }
        }
    }

    private var _bind: FragmentMovieDetailsBinding? = null
    private val bind get() = _bind!!
    private var movie: Movie? = null
    private lateinit var adapter: MoviesDetailsActorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMovieDetailsBinding.bind(view)
        movie = requireArguments().getParcelable(KEY_MOVIE)

        // (!) callback way
        adapter = MoviesDetailsActorAdapter {
            showSnackBarActor(it)
        }
        val recyclerView = bind.actorRecycler
        recyclerView.adapter = adapter

        if (movie?.actors.isNullOrEmpty()) {
            bind.actorRecycler.visibility = View.INVISIBLE
            bind.cast.visibility = View.INVISIBLE
        }


        val reviewsTemplate = requireContext().getString(R.string.reviews, movie?.numberOfRatings)
        val ageRatingTemplate = requireContext().getString(R.string.age_rating, movie?.minimumAge)

        bind.apply {
            movieTitle.text = movie?.title
            reviews.text = reviewsTemplate
            ageRating.text = ageRatingTemplate
            genreTags.text = movie?.genres?.map { it.name }.toString()
            overview.text = movie?.overview

            backText.setOnClickListener {
                requireActivity().supportFragmentManager
                    .popBackStack()
            }
        }

        movie?.backdrop?.let { bind.backdrop.loadImage(it) }
        (movie?.ratings?.div(2))?.let { setRating(bind, it.toInt()) }

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
            bind.actorRecycler,
            actor.name,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}