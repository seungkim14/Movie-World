package com.seungleekim.android.movie.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seungleekim.android.movie.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class FavoriteMoviesDatabase : RoomDatabase() {

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}