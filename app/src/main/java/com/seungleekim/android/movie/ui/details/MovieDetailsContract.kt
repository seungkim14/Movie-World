package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.ui.BaseContract

interface MovieDetailsContract {

    interface View : BaseContract.View<Presenter> {

        fun showMovieDetails(movieDetails: MovieDetails)

        fun showMovieTrailer()

        fun showGetTickets()

        fun showShare()

        fun setFavorite(b: Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getMovieDetails(trendingMovie: TrendingMovie)

        fun addToFavorite(b: Boolean)
    }
}
