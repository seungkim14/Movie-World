package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Review
import com.seungleekim.android.movie.model.Trailer
import com.seungleekim.android.movie.ui.BaseContract

interface MovieDetailsContract {

    interface View : BaseContract.View<Presenter> {
        fun showMovieDetails(movieDetails: MovieDetails)
        fun showFavorite(setFavorite: Boolean, buttonClicked: Boolean)
        fun showFailureMessage(message: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getMovieDetails(movie: Movie)
        fun getFavorite(movie: Movie)
        fun setFavorite(setFavorite: Boolean, buttonClicked: Boolean = false)
        fun insertFavoriteMovie(movie: Movie)
        fun deleteFavoriteMovie(movie: Movie)
        fun onFavoriteFabClick(movie: Movie)
    }
}
