package com.backbase.assignment.viewmodel

import androidx.lifecycle.*
import com.backbase.assignment.model.ResponseData
import com.backbase.assignment.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 2
    }

    val moviePostersResult : LiveData<ResponseData> =
    liveData {
        val moviePosters = repository.getMoviePosters().asLiveData(Dispatchers.Main)
        emitSource(moviePosters)
    }

    val moviesResult: LiveData<ResponseData> =
        liveData {
            val movies = repository.getMovies().asLiveData(Dispatchers.Main)
            emitSource(movies)
    }

    private val movieDetailsLiveData = MutableLiveData<String>()
    val movieDetailsResult: LiveData<ResponseData> = movieDetailsLiveData.switchMap { id ->
        liveData {
            val movieDetails = repository.getMovieDetails(id).asLiveData(Dispatchers.Main)
            emitSource(movieDetails)
        }
    }

    /**
     * retrieve the movie details based on the id.
     */
    fun getMovieDetails(id: String) {
        movieDetailsLiveData.postValue(id)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                viewModelScope.launch {
                    repository.requestMore()
            }
        }
    }
}