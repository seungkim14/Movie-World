package com.seungleekim.android.movie.persistence

import com.seungleekim.android.movie.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class FavoriteMoviesStore(
    private val favoriteMoviesDao: FavoriteMoviesDao
) {

    fun insertFavoriteMovie(movie: Movie): Completable {
        return favoriteMoviesDao.insertFavoriteMovie(movie)
    }

    fun insertFavoriteMovies(movies: List<Movie>): Completable {
        return favoriteMoviesDao.insertFavoriteMovies(movies)
    }

    fun deleteFavoriteMovie(movie: Movie): Completable {
        return favoriteMoviesDao.deleteFavoriteMovie(movie)
    }

    fun deleteFavoriteMovies(movies: List<Movie>): Completable {
        return favoriteMoviesDao.deleteFavoriteMovies(movies)
    }

    fun getFavoriteMovies(): Flowable<List<Movie>> {
        return favoriteMoviesDao.getFavoriteMovies()
    }

    fun getFavoriteMovieById(movieId: Int): Maybe<Movie> {
        return favoriteMoviesDao.getFavoriteMovieById(movieId)
    }
}