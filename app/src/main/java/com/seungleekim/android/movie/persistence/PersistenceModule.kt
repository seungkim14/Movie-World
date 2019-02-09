package com.seungleekim.android.movie.persistence

import androidx.room.Room
import com.seungleekim.android.movie.MovieWorldApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideFavoriteMoviesDatabase(application: MovieWorldApplication): FavoriteMoviesDatabase {
        return Room.databaseBuilder(application, FavoriteMoviesDatabase::class.java, "FavoriteMovies_db").build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMoviesDao(favoriteMoviesDatabase: FavoriteMoviesDatabase): FavoriteMoviesDao {
        return favoriteMoviesDatabase.favoriteMoviesDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteMoviesStore(favoriteMoviesDao: FavoriteMoviesDao): FavoriteMoviesStore {
        return FavoriteMoviesStore(favoriteMoviesDao)
    }
}