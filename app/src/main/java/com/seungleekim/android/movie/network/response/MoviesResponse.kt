package com.seungleekim.android.movie.network.response

import com.google.gson.annotations.SerializedName
import com.seungleekim.android.movie.model.Movie

class MoviesResponse {
    @SerializedName("results")
    lateinit var trendingMovies: List<Movie>
}
