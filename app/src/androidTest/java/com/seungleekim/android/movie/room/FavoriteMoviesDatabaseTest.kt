package com.seungleekim.android.movie.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.seungleekim.android.movie.model.Movie
import io.reactivex.functions.Predicate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteMoviesDatabaseTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val MOVIES = listOf(
        Movie(1, "title1", "posterPath1", "releaseDate1"),
        Movie(2, "title2", "posterPath2", "releaseDate2"),
        Movie(3, "title3", "posterPath3", "releaseDate3"),
        Movie(4, "title4", "posterPath4", "releaseDate4"),
        Movie(5, "title5", "posterPath5", "releaseDate5")
    )

    private lateinit var favoriteMoviesDao: FavoriteMoviesDao
    private lateinit var favoriteMoviesDatabase: FavoriteMoviesDatabase

    @Before
    fun createFavoriteMoviesDatabase() {
        favoriteMoviesDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            FavoriteMoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteMoviesDao = favoriteMoviesDatabase.favoriteMoviesDao()
    }

    @After
    fun closeDb() {
        favoriteMoviesDatabase.close()
    }

    @Test
    fun insertAndGetMovieByIdTest() {
        // Insert a movie
        favoriteMoviesDao.insertFavoriteMovie(MOVIES[0]).blockingAwait()

        // Get By Id
        favoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertValue(MOVIES[0])
    }

    @Test
    fun insertAndGetMoviesTest() {
        // Insert movies
        favoriteMoviesDao.insertFavoriteMovies(MOVIES).blockingAwait()

        // Get all of them
        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue(MOVIES)
    }

    @Test
    fun deleteAndGetMovieTest() {
        // Insert
        favoriteMoviesDao.insertFavoriteMovie(MOVIES[0]).blockingAwait()

        // Delete
        favoriteMoviesDao.deleteFavoriteMovie(MOVIES[0]).blockingAwait()

        // Check if it still exists
        favoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertNoValues()
    }

    @Test
    fun deleteAndGetMoviesTest() {
        // Insert 5 movies
        favoriteMoviesDao.insertFavoriteMovies(MOVIES).blockingAwait()

        // Delete only 2 of them
        favoriteMoviesDao.deleteFavoriteMovies(listOf(MOVIES[0], MOVIES[3])).blockingAwait()

        // Check deletions
        favoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertNoValues()
        favoriteMoviesDao.getFavoriteMovieById(MOVIES[3].id)
            .test()
            .assertNoValues()

        // Check if other three movies still exist
        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue(object : Predicate<List<Movie>> {
                override fun test(movies: List<Movie>): Boolean {
                    return movies.containsAll(listOf(MOVIES[1], MOVIES[2], MOVIES[4]))
                }
            })
    }
}