package ru.asshands.softwire.androidacademy2020.adapters

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.data.Movie
import ru.asshands.softwire.androidacademy2020.databinding.MoviesListItemBinding
import ru.asshands.softwire.androidacademy2020.loadImage


class MoviesListAdapter(
    private val sharedPreferences: SharedPreferences,
    private val clickListener: OnRecyclerItemMovieClicked
) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var _bind: MoviesListItemBinding? = null
    private val bind get() = _bind!!
    private var list: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _bind = MoviesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(sharedPreferences, bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun bindData(newList: List<Movie>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val sharedPreferences: SharedPreferences,
        private val bind: MoviesListItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bindView(item: Movie) {
            val ctx = itemView.context

            val reviewsTemplate = ctx.getString(R.string.reviews, item.numberOfRatings)
            val ageRatingTemplate = ctx.getString(R.string.age_rating, item.minimumAge)
            val runTimeTemplate = ctx.getString(R.string.running_time, item.runtime)
            var favorite = loadFavorite(item.id)

            bind.apply {
                itemMovieTitle.text = item.title
                itemReviews.text = reviewsTemplate
                itemAgeRaiting.text = ageRatingTemplate
                moviesListItemRunningTime.text = runTimeTemplate
                itemGenreTags.text = item.genres.map { it.name }.toString()
                itemPoster.loadImage(item.poster)
                itemFavorite.setOnClickListener {
                    if (favorite) {
                        favorite = false
                        saveFavorite(item.id, favorite)
                        itemFavorite.setImageResource(R.drawable.favorite_gray)
                    } else {
                        favorite = true
                        saveFavorite(item.id, favorite)
                        itemFavorite.setImageResource(R.drawable.favorite_active)
                    }
                }
            }

            setRating(bind, (item.ratings / 2).toInt())
            if (favorite) bind.itemFavorite.setImageResource(R.drawable.favorite_active)
            else bind.itemFavorite.setImageResource(R.drawable.favorite_gray)
        }

        private fun setRating(bind: MoviesListItemBinding, rating: Int) {
            val star1 = bind.itemStar1
            val star2 = bind.itemStar2
            val star3 = bind.itemStar3
            val star4 = bind.itemStar4
            val star5 = bind.itemStar5

            if (rating >= 1) star1.setImageResource(R.drawable.star_icon_red)
            if (rating >= 2) star2.setImageResource(R.drawable.star_icon_red)
            if (rating >= 3) star3.setImageResource(R.drawable.star_icon_red)
            if (rating >= 4) star4.setImageResource(R.drawable.star_icon_red)
            if (rating >= 5) star5.setImageResource(R.drawable.star_icon_red)
        }

        private fun saveFavorite(movieId: Int, favorite: Boolean) {
            val ed = sharedPreferences.edit()
            ed.putBoolean(movieId.toString(), favorite)
            ed.apply()
            Log.d("DEBUG-TAG", "id=$movieId favorite=$favorite saved")
        }

        private fun loadFavorite(movieId: Int): Boolean =
            sharedPreferences.getBoolean(movieId.toString(), false)
    }
}

interface OnRecyclerItemMovieClicked {
    fun onClick(movie: Movie)
}