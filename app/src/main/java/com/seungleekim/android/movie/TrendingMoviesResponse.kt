package com.seungleekim.android.movie

import com.google.gson.annotations.SerializedName

class TrendingMoviesResponse {
    @SerializedName("results")
    lateinit var trendingMovies: List<Movie>
}
