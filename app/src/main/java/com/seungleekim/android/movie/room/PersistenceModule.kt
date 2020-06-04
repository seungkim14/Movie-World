package com.seungleekim.android.movie.room

import androidx.room.Room
import com.seungleekim.android.movie.MovieWorldApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val FAVORITE_MOVIES_DB_NAME = "FavoriteMovies_db"

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideFavoriteMoviesDatabase(application: MovieWorldApplication): FavoriteMoviesDatabase {
        return Room.databaseBuilder(
            application,
            FavoriteMoviesDatabase::class.java,
            FAVORITE_MOVIES_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMoviesDao(favoriteMoviesDatabase: FavoriteMoviesDatabase): FavoriteMoviesDao {
        return favoriteMoviesDatabase.favoriteMoviesDao()
    }
}