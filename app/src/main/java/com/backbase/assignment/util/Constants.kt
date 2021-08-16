package com.backbase.assignment.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://api.themoviedb.org/"
const val POSTER_BASE_URL="https://image.tmdb.org/t/p/original/"
const val MOVIE_ID = "MOVIE_ID"
class Constants{
    companion object{
        fun getHoursAndMinutes(minutes: Int): String{
           val hoursMins = (minutes/60).toString() +"h "+(minutes%60).toString()+"m"
            return hoursMins
        }

        fun getDateAndMonth(releaseDate:String):String{
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            var formattedDate = ""
            try {
                val date: Date = formatter.parse(releaseDate)
                val out: DateFormat = SimpleDateFormat("MMM dd, yyyy")
                formattedDate = out.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return formattedDate
        }
    }
}