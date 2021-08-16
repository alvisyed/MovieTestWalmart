package com.backbase.assignment.model


import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("vote_count") val voteCount: Int?,
    @field:SerializedName("poster_path") val posterPath: String?,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("backdrop_path") val backdropPath: String?,
    @field:SerializedName("original_title") val originalTitle: String?,
    @field:SerializedName("genre_ids") val genreIds: List<Int>?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("vote_average") val voteAverage: Double?,
    @field:SerializedName("overview") val overview: String?,
    @field:SerializedName("release_date") val releaseDate: String?
);