package ru.asshands.softwire.androidacademy2020.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.databinding.MoviesListItemBinding
import ru.asshands.softwire.androidacademy2020.models.Movie

class MoviesListAdapter(private val clickListener: OnRecyclerItemMovieClicked) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var _bind: MoviesListItemBinding? = null
    private val bind get() = _bind!!
    private var list: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _bind = MoviesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
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

    class ViewHolder(private val bind: MoviesListItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bindView(item: Movie) {

            val reviewsTemplate = itemView.context.getString(R.string.reviews, item.reviews)
            val runningTimeTemplate =
                itemView.context.getString(R.string.running_time, item.runningTime)
            bind.apply {
                moviesListItemTitle.text = item.title
                moviesListItemGenreTags.text = item.genreTags
                moviesListItemReviews.text = reviewsTemplate
                moviesListItemRunningTime.text = runningTimeTemplate
                moviesListAgeRaiting.text = item.ageRating
                moviesListItemPoster.setImageResource(item.posterResource)
            }
            if (item.favorite) bind.moviesListItemFavorite.setImageResource(R.drawable.favorite_active)
            setRating(bind, item.rating)
        }

        private fun setRating(bind: MoviesListItemBinding, rating: Int) {
            val star1 = bind.moviesListItemStar1
            val star2 = bind.moviesListItemStar2
            val star3 = bind.moviesListItemStar3
            val star4 = bind.moviesListItemStar4
            val star5 = bind.moviesListItemStar5

            if (rating >= 1) star1.setImageResource(R.drawable.star_icon_red)
            if (rating >= 2) star2.setImageResource(R.drawable.star_icon_red)
            if (rating >= 3) star3.setImageResource(R.drawable.star_icon_red)
            if (rating >= 4) star4.setImageResource(R.drawable.star_icon_red)
            if (rating >= 5) star5.setImageResource(R.drawable.star_icon_red)
        }
    }
}

interface OnRecyclerItemMovieClicked {
    fun onClick(movie: Movie)
}