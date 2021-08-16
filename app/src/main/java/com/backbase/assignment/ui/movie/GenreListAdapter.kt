package com.backbase.assignment.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.model.Genre

class GenreListAdapter(var items: List<Genre>) :
    RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.genre_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Genre = items[position] as Genre
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var genreTextView: TextView

        fun bind(item: Genre) = with(itemView) {
            genreTextView = itemView.findViewById(R.id.genre)
            genreTextView.text = item.name
        }
    }
}