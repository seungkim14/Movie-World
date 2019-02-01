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
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>
) : Parcelable {

    fun getPosterUrl(): String {
        return "http://image.tmdb.org/t/p/w342$posterPath"
    }

    fun getBackdropUrl(): String {
        return "http://image.tmdb.org/t/p/w780$backdropPath"
    }

    fun getGenres(): String {
        val genreNames = mutableListOf<String>()
        genreIds.forEach {
            genreNames.add(getGenreById(it))
        }
        return genreNames.joinToString()
    }

    private fun getGenreById(genreId: Int): String {
        return when (genreId) {
            28 -> "Action"
            12 -> "Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            14 -> "Fantasy"
            36 -> "History"
            27 -> "Horror"
            10402 -> "Music"
            9648 -> "Mystery"
            10749 -> "Romance"
            878 -> "Science Fiction"
            10770 -> "TV Movie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            else -> "NOT FOUND"
        }
    }
}
