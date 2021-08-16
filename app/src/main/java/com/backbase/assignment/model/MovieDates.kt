package com.backbase.assignment.model


import com.google.gson.annotations.SerializedName

data class MovieDates (
    @field:SerializedName("maximum")
    val maximum: String?,

    @field:SerializedName("minimum")
     val minimum: String?
);