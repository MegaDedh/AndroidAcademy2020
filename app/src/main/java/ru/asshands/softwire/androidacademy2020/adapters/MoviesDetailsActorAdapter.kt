package ru.asshands.softwire.androidacademy2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.databinding.MoviesDetailActorItemBinding
import ru.asshands.softwire.androidacademy2020.models.Actor

// (!) callback way
class MoviesDetailsActorAdapter(private val clickListener: (Actor) -> Unit) :
    RecyclerView.Adapter<MoviesDetailsActorAdapter.ViewHolder>() {

    private var list: List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movies_detail_actor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
        // (!) callback way
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun bindData(newList: List<Actor>) {
        list = newList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val bind = MoviesDetailActorItemBinding.bind(itemView)
        fun bindView(item: Actor) {

            bind.moviesDetailActorItemPhoto.setImageDrawable(item.posterResource)
            bind.moviesDetailActorItemName.text = item.name
        }
    }
}

/*interface OnRecyclerItemActorClicked {
    fun onClick(actor: Actor)
}*/


