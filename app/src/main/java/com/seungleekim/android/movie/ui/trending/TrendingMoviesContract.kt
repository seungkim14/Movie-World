package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.network.response.MoviesResponse
import com.seungleekim.android.movie.ui.BaseContract

interface TrendingMoviesContract {

    interface View : BaseContract.View<Presenter> {
        fun showTrendingMovies(movies: List<Movie>?)
        fun showFailureMessage(errorMessage: String)
        fun showMovieDetail(movie: Movie)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getTrendingMovies()
        fun onGetMoviesSuccess(response: MoviesResponse?)
        fun onGetMoviesFailure(e: Throwable?)
    }
}