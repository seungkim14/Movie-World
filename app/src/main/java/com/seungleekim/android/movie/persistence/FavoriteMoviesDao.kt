package com.seungleekim.android.movie.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.seungleekim.android.movie.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovies(movies: List<Movie>): Completable

    @Delete
    fun deleteFavoriteMovie(movie: Movie): Completable

    @Delete
    fun deleteFavoriteMovies(movies: List<Movie>): Completable

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getFavoriteMovieById(id: Int): Maybe<Movie>

    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): Flowable<List<Movie>>

}