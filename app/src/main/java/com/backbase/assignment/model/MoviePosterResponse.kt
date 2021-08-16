package com.backbase.assignment.model


import com.google.gson.annotations.SerializedName

data class MoviePosterResponse(
    @field:SerializedName("results") val resultData: List<Movie>?,
    @field:SerializedName("page") val page: Int? = null,
    @field:SerializedName("total_results") val totalResults: Int?,
    @field:SerializedName("dates") val dates: MovieDates?,
    @field:SerializedName("total_pages") val totalPages: Int? = null
);