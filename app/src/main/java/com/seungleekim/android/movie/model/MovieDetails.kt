package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import timber.log.Timber

@Parcelize
data class MovieDetails(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val rating: Double,
    val mpaaRating: String?,
    val runtime: Int,
    val genreIds: List<Int>,
    val releaseDate: String,
    val trailer: Trailer,
    val overview: String,
    val credits: Credits,
    val reviews: List<Review>
) : Parcelable {

    fun getTitleWithYear(): String {
        return "$title (${getYear()})"
    }

    private fun getYear(): String{
        return releaseDate.split("-")[0]
    }

    fun getRatingText(): String {
        return "Rate: $rating"
    }

    fun getBackdropUrl(): String {
        val backdropUrl = "http://image.tmdb.org/t/p/w780$backdropPath"
        Timber.d(backdropUrl)
        return backdropUrl
    }

    fun getFirstGenre(): String {
        return getGenreById(genreIds.get(0))
    }

    fun getRuntimeString(): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "$hours hr $minutes min"
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
            10770 -> "TV TrendingMovie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            else -> "NOT FOUND"
        }
    }
}