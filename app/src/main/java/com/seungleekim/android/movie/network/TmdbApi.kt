package com.seungleekim.android.movie.network

import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TmdbApi {

    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    fun getTrendingMovies(): Single<MoviesResponse>
}