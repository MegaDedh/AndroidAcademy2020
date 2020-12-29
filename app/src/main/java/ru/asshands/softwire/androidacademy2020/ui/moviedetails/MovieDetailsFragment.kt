package ru.asshands.softwire.androidacademy2020.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMovieDetailsBinding
import ru.asshands.softwire.androidacademy2020.loadImage


class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by activityViewModels()
    private var _bind: FragmentMovieDetailsBinding? = null
    private val bind get() = _bind!!
    private lateinit var adapter: MoviesDetailsActorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMovieDetailsBinding.bind(view)

        viewModel.moviesList.observe(this.viewLifecycleOwner, this::setUiElements)
        setActorRecyclerView()
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }

    private fun setUiElements(movie: Movie) {
        if (movie.actors.isNullOrEmpty()) {
            bind.actorRecyclerView.visibility = View.INVISIBLE
            bind.cast.visibility = View.INVISIBLE
        } else {
            movie.actors.let { adapter.bindData(it) }
        }

        val reviewsTemplate = requireContext().getString(R.string.reviews, movie.numberOfRatings)
        val ageRatingTemplate = requireContext().getString(R.string.age_rating, movie.minimumAge)

        bind.apply {
            movieTitle.text = movie.title
            reviews.text = reviewsTemplate
            ageRating.text = ageRatingTemplate
            genreTags.text = movie.genres.map { it.name }.toString()
            overview.text = movie.overview

            backText.setOnClickListener {
                requireActivity().supportFragmentManager
                    .popBackStack()
            }
        }

        movie.backdrop.let { bind.backdrop.loadImage(it) }
        setRating(bind, (movie.ratings.div(2)).toInt())
    }

    private fun setActorRecyclerView() {
        // (!) callback way
        adapter = MoviesDetailsActorAdapter {
            showSnackBarActor(it)
        }
        val recyclerView = bind.actorRecyclerView
        recyclerView.adapter = adapter
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
            bind.actorRecyclerView,
            actor.name,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}