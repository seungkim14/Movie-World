package com.seungleekim.android.movie

import io.reactivex.Observable
import retrofit2.http.GET

interface TmdbApi {

    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    fun getTrendingMovies(): Observable<TrendingMoviesResponse>
}