package com.backbase.assignment.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.di.Injection
import com.backbase.assignment.model.ResponseData
import com.backbase.assignment.model.Movie
import com.backbase.assignment.ui.movie.MoviePostersAdapter
import com.backbase.assignment.ui.movie.MoviesAdapter
import com.backbase.assignment.util.MOVIE_ID
import com.backbase.assignment.viewmodel.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), MoviesAdapter.OnMovieItemClickListener, MoviePostersAdapter.onPosterItemClickListener {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesRecyclerView: RecyclerView

    private lateinit var moviePostersAdapter: MoviePostersAdapter
    private lateinit var moviePostersRecyclerView: RecyclerView

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MoviesViewModel::class.java)

        moviesRecyclerView = findViewById(R.id.movie_recyclerview)
        moviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        moviePostersRecyclerView = findViewById(R.id.poster_recyclerview)
        moviePostersRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        fetchMovies()
        fetchMoviePosters()
        setupScrollListener()
    }


    private fun fetchMovies() {
        viewModel.moviesResult.observe(this) { result ->
            when (result) {
                is ResponseData.Success -> {
                    moviesAdapter = MoviesAdapter(result.data as List<Movie>, this)
                    moviesRecyclerView.adapter = moviesAdapter
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

        private fun fetchMoviePosters() {
            viewModel.moviePostersResult.observe(this) { result ->
                when (result) {
                    is ResponseData.Success -> {
                        moviePostersAdapter = MoviePostersAdapter(result.data as List<Movie>, this)
                        moviePostersRecyclerView.adapter = moviePostersAdapter
                        moviePostersAdapter.notifyDataSetChanged()
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

    private fun setupScrollListener() {
        val layoutManager = moviesRecyclerView.layoutManager as LinearLayoutManager
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    override fun onClick(id: String) {
        val intent = Intent(baseContext, MovieDetailActivity::class.java).apply {
            putExtra(MOVIE_ID, id)
        }
        startActivity(intent)

    }

}
