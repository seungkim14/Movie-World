package com.seungleekim.android.movie.model

data class MovieDetails(
    var id: Int,
    var backdropPath: String?,
    var genreIds: List<Int>?,
    var overview: String?,
    var releaseDate: String?,
    var duration: Int?,
    var rating: Double?,
    var trailer: Trailer?,
    var reviews: List<Review>?,
    var mpaaRating: String?,
    var credits: Credits?
) {

    fun getBackdropUrl(): String {
        return "http://image.tmdb.org/t/p/w780$backdropPath"
    }

    fun getGenres(): String {
        val genreNames = mutableListOf<String>()
        genreIds?.forEach {
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