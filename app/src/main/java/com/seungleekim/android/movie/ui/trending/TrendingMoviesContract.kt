package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.network.response.MoviesResponse
import com.seungleekim.android.movie.ui.BaseContract

interface TrendingMoviesContract {

    interface View : BaseContract.View<Presenter> {
        fun showTrendingMovies(trendingMovies: List<TrendingMovie>?)
        fun showFailureMessage()
        fun showMovieDetail(trendingMovie: TrendingMovie)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadTrendingMovies()
        fun onGetMoviesSuccess(response: MoviesResponse?)
        fun onGetMoviesFailure(e: Throwable?)
    }
}
