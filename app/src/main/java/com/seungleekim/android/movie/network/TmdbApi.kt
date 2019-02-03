package com.seungleekim.android.movie.network

import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApi {

    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    fun getTrendingMovies(): Single<MoviesResponse>

    @GET("3/movie/{movieId}?language=en-US&&append_to_response=videos,reviews,release_dates,credits")
    fun getMovieDetail(@Path("movieId") movieId: Int): Single<MovieDetails>
}
