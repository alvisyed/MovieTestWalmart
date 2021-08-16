package com.backbase.assignment.network

import com.backbase.assignment.model.MovieDetail
import com.backbase.assignment.model.MoviePosterResponse
import com.backbase.assignment.util.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    suspend fun getMoviePosters(@Query("language") language: String, @Query("page") page: String, @Query("api_key") api_key : String): MoviePosterResponse

    @GET("3/movie/popular")
    suspend fun getMovies(@Query("language") language: String, @Query("page") page: Int, @Query("api_key") api_key : String): MoviePosterResponse

    @GET("3/movie/{id}")
    suspend fun getMovieDetails(@Path(value="id") id:String, @Query("api_key") api_key : String) : MovieDetail

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}