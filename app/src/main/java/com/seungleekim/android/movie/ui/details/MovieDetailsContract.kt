package com.seungleekim.android.movie.ui.details

import android.annotation.SuppressLint
import com.seungleekim.android.movie.model.*
import com.seungleekim.android.movie.ui.BaseContract

interface MovieDetailsContract {

    interface View : BaseContract.View<Presenter> {
        fun showMovieDetails(movieDetails: MovieDetails)
        fun showMovieTitle(title: String)
        fun showMovieBackdrop(backdropUrl: String)
        fun showMovieRating(rating: String)
        fun showMovieMpaaRating(mpaaRating: String?)
        fun showMovieDuration(runtime: String)
        fun showMovieGenres(genres: String)
        fun showMovieReleaseDate(releaseDate: String)
        fun showFavorite(b: Boolean)
        fun showMovieTrailers(trailers: List<Trailer>)
        fun showMovieOverview(overview: String?)
        fun showMovieCasts(casts: String?)
        fun showMovieCrews(crews: String?)
        fun showMovieReviews(reviews: String?)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getMovieDetails(trendingMovie: TrendingMovie)
        fun getFavorite(trendingMovie: TrendingMovie)
        fun setFavorite(b: Boolean)
    }
}
