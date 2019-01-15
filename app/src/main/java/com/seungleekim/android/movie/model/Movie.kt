package com.seungleekim.android.movie.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("release_date")
    val releaseDate: String
) : Parcelable {

    fun getPosterUrl(): String {
        return return "http://image.tmdb.org/t/p/w342$posterPath"
    }
}
