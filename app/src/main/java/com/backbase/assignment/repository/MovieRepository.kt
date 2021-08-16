package com.backbase.assignment.repository

import com.backbase.assignment.model.ResponseData
import com.backbase.assignment.model.Movie
import com.backbase.assignment.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

@ExperimentalCoroutinesApi
class MovieRepository(private val service: ApiService) {
    // keep the list of all movie results received
    private val inMemoryCache = mutableListOf<Movie>()

    // keep channel of results. The channel allows us to broadcast updates so
    // the subscriber will have the latest data
    private val movies = ConflatedBroadcastChannel<ResponseData>()
    private val moviePosters = ConflatedBroadcastChannel<ResponseData>()
    private val movieDetails = ConflatedBroadcastChannel<ResponseData>()



    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = MOVIE_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isMoviesRequestInProgress = false
    private var isMoviePostersRequestInProgress = false



    suspend fun getMovies(): Flow<ResponseData> {
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestMoviesAndSaveData()
        return movies.asFlow()
    }
    suspend fun getMoviePosters(): Flow<ResponseData> {
        requestMoviePosters()
        return moviePosters.asFlow()
    }

    suspend fun getMovieDetails(id : String) : Flow<ResponseData>{
        try{
            val response = service.getMovieDetails(id,api_key)
            movieDetails.offer(ResponseData.Success(response))
        } catch (exception: IOException) {
            movieDetails.offer(ResponseData.Error(exception))
        } catch (exception: HttpException) {
            movieDetails.offer(ResponseData.Error(exception))
        }
        return movieDetails.asFlow()
    }
    suspend fun requestMore() {
        if (isMoviesRequestInProgress) return
        val successful = lastRequestedPage++> max_pages
        if (!successful) {
            requestMoviesAndSaveData()
        }
    }

    suspend fun retry(query: String) {
        if (isMoviesRequestInProgress) return
        requestMoviesAndSaveData()
    }

    private suspend fun requestMoviePosters():Boolean{
        isMoviePostersRequestInProgress = true
        var moviePosterRequestSuccessful = false
        try{
            val response = service.getMoviePosters(language, page_undefined,api_key)
            val results = response.resultData ?: emptyList()
            moviePosters.offer(ResponseData.Success(results))
            moviePosterRequestSuccessful = true
        } catch (exception: IOException) {
            moviePosters.offer(ResponseData.Error(exception))
        } catch (exception: HttpException) {
            moviePosters.offer(ResponseData.Error(exception))
        }
        isMoviePostersRequestInProgress = false
        return moviePosterRequestSuccessful
    }
    private suspend fun requestMoviesAndSaveData(): Boolean {
        isMoviesRequestInProgress = true
        var successful = false

        try {
            val response = service.getMovies(language, lastRequestedPage,api_key)
            val results = response.resultData ?: emptyList()
            inMemoryCache.addAll(results)
            val moviesRepo = reposByName()
            movies.offer(ResponseData.Success(moviesRepo))
            successful = true
        } catch (exception: IOException) {
            movies.offer(ResponseData.Error(exception))
        } catch (exception: HttpException) {
            movies.offer(ResponseData.Error(exception))
        }
        isMoviesRequestInProgress = false
        return successful
    }

    private fun reposByName(): List<Movie> {
        return inMemoryCache.sortedWith(compareByDescending<Movie> { it.voteCount }.thenBy { it.originalTitle })
    }

    companion object {
        private const val max_pages =500
        private const val page_undefined="undefined"
        private const val language ="en_us"
        private const val api_key="55957fcf3ba81b137f8fc01ac5a31fb5"
    }
}