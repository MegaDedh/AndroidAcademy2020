package ru.asshands.softwire.androidacademy2020.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.databinding.MoviesDetailActorItemBinding
import ru.asshands.softwire.androidacademy2020.loadImage


// (!) callback way
class MoviesDetailsActorAdapter(private val clickListener: (Actor) -> Unit) :
    RecyclerView.Adapter<MoviesDetailsActorAdapter.ViewHolder>() {

    private var _bind: MoviesDetailActorItemBinding? = null
    private val bind get() = _bind!!
    private var list: List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _bind = MoviesDetailActorItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
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
        notifyDataSetChanged()
    }

    class ViewHolder(private val bind: MoviesDetailActorItemBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bindView(item: Actor) {
            bind.moviesDetailActorItemPhoto.loadImage(item.picture)
            bind.moviesDetailActorItemName.text = item.name
        }
    }
}