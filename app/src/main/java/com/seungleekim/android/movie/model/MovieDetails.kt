package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    val trailers: List<Trailer>,
    val overview: String,
    val crews: List<Crew>,
    val casts: List<Cast>,
    val reviews: List<Review>
) : Parcelable

fun MovieDetails.getTitleWithYear(): String {
    return "$title (${getYear()})"
}

fun MovieDetails.getRatingText(): String {
    return "Rate: $rating"
}

fun MovieDetails.getBackdropUrl() = "http://image.tmdb.org/t/p/w780$backdropPath"

fun MovieDetails.getFirstGenre(): String {
    return getGenreById(genreIds[0])
}

fun MovieDetails.getRuntimeString(): String {
    val hours = runtime / 60
    val minutes = runtime % 60
    return "$hours hr $minutes min"
}

fun MovieDetails.getCastsString() = listToString(casts)

fun MovieDetails.getCrewsString() = listToString(crews)

fun MovieDetails.getReviewsString() = listToString(reviews)

private fun MovieDetails.getYear(): String {
    return releaseDate.split("-")[0]
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
        else -> ""
    }
}

private fun listToString(list: List<Any>): String? {
    if (list.isEmpty()) {
        return null
    }
    var lineBreak = ""
    val sb = StringBuilder()
    for (element in list) {
        sb.append(lineBreak).append(element.toString())
        lineBreak = "\n"
    }
    return sb.toString()
}