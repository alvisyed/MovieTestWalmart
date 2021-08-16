package com.backbase.assignment.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.model.Movie
import com.backbase.assignment.util.POSTER_BASE_URL
import com.squareup.picasso.Picasso

class MoviePostersAdapter(var items: List<Movie>, val mListener: onPosterItemClickListener) :
    RecyclerView.Adapter<MoviePostersAdapter.ViewHolder>() {

    interface onPosterItemClickListener{
        fun onClick(id:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.poster_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item:Movie = items[position] as Movie
        holder.bind(item)
        holder.poster.setOnClickListener {
            mListener.onClick(item.id.toString())
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var posterImage: ImageView
        lateinit var poster:View

        fun bind(item: Movie) = with(itemView) {
            posterImage = itemView.findViewById(R.id.imageView)
            poster=itemView.findViewById(R.id.poster)
            Picasso.get()
                .load(POSTER_BASE_URL+ item.posterPath)
                .into(posterImage)
        }
    }
}