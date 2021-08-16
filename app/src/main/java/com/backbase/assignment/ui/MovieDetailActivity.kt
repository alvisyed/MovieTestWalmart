package com.backbase.assignment.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.di.Injection
import com.backbase.assignment.model.MovieDetail
import com.backbase.assignment.model.ResponseData
import com.backbase.assignment.ui.movie.GenreListAdapter
import com.backbase.assignment.util.Constants
import com.backbase.assignment.util.MOVIE_ID
import com.backbase.assignment.util.POSTER_BASE_URL
import com.backbase.assignment.viewmodel.MoviesViewModel
import com.squareup.picasso.Picasso


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var poster: ImageView
    private lateinit var titleView: TextView
    private lateinit var releaseDateView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var backArrow : ImageView
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var genreAdapter :GenreListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MoviesViewModel::class.java)
        val id = intent.getStringExtra(MOVIE_ID)

        poster = findViewById(R.id.poster)
        titleView =findViewById(R.id.title)
        releaseDateView = findViewById(R.id.releaseDate)
        descriptionView =findViewById(R.id.description)
        backArrow = findViewById(R.id.backArrow)
        genreRecyclerView = findViewById(R.id.genreRecyclerview)
        genreRecyclerView.setLayoutManager(GridLayoutManager(this, 3))
        backArrow.setOnClickListener(){
            onBackPressed()
        }
        if (id != null) {
            fetchMovieDetails(id)
        }
    }

    private fun fetchMovieDetails(id: String) {
        viewModel.getMovieDetails(id)
        viewModel.movieDetailsResult.observe(this) { result ->
            when (result) {
                is ResponseData.Success -> {
                    val movieDetail = result.data as MovieDetail
                    Picasso.get()
                        .load(POSTER_BASE_URL + movieDetail.posterPath)
                        .into(poster)
                    titleView.text = movieDetail.originalTitle
                    releaseDateView.text=Constants.getDateAndMonth(movieDetail.releaseDate) +" - "+
                            Constants.getHoursAndMinutes(movieDetail.runtime)
                    descriptionView.text = movieDetail.overview
                    genreAdapter = GenreListAdapter(movieDetail.genres)
                    genreRecyclerView.adapter = genreAdapter
                }
                is ResponseData.Error -> {
                    Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

