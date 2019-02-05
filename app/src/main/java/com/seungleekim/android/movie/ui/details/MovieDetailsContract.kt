package com.seungleekim.android.movie.ui.details

import android.annotation.SuppressLint
import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Trailer
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
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getMovieDetails(trendingMovie: TrendingMovie)
        fun getFavorite(trendingMovie: TrendingMovie)
        fun addToFavorite(b: Boolean)
    }
}
