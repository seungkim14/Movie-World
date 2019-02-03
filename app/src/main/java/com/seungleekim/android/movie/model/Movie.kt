package com.seungleekim.android.movie.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String
) : Parcelable {

    @IgnoredOnParcel
    var movieDetails: MovieDetails? = null

    fun getPosterUrl(): String {
        return "http://image.tmdb.org/t/p/w342$posterPath"
    }
}
