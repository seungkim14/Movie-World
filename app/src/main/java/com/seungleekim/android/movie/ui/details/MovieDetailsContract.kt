package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Review
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
        fun showFavorite(setFavorite: Boolean, buttonClicked: Boolean)
        fun showMovieTrailers(trailers: List<Trailer>)
        fun showMovieOverview(overview: String?)
        fun showMovieCasts(casts: String?)
        fun showMovieCrews(crews: String?)
        fun showMovieReviews(reviews: List<Review>)
        fun showFailureMessage()
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
