package com.backbase.assignment.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.model.Movie
import com.backbase.assignment.ui.custom.RatingView
import com.backbase.assignment.util.Constants
import com.backbase.assignment.util.POSTER_BASE_URL
import com.squareup.picasso.Picasso


class MoviesAdapter(var items: List<Movie>, val mListener:OnMovieItemClickListener) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    interface OnMovieItemClickListener{
        fun onClick(id:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item:Movie = items[position] as Movie
        holder.bind(item)
        holder.movie.setOnClickListener {
            mListener.onClick(item.id.toString())
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView
        lateinit var title: TextView
        lateinit var releaseDate: TextView
        lateinit var movie: View
        lateinit var rating: RatingView

        fun bind(item: Movie) = with(itemView) {
            movie = itemView.findViewById(R.id.movie);
            poster = itemView.findViewById(R.id.poster)
            Picasso.get()
                .load(POSTER_BASE_URL + item.posterPath)
                .into(poster)
            title = itemView.findViewById(R.id.title)
            title.text = item.title
            releaseDate = itemView.findViewById(R.id.releaseDate)
            releaseDate.text = item.releaseDate?.let { Constants.getDateAndMonth(it) }
            rating =itemView.findViewById(R.id.rating)
            if(item.popularity > 50f){
                rating.setProgressColor(resources.getColor(R.color.colorPrimary))
            } else{
                rating.setProgressColor(resources.getColor(R.color.appYellow))
            }
            rating.setTextColor(resources.getColor(R.color.white))
            rating.setProgress(item.popularity.toInt())
        }
    }


}