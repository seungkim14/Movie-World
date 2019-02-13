package com.seungleekim.android.movie.persistence

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

    private lateinit var mFavoriteMoviesDao: FavoriteMoviesDao
    private lateinit var mFavoriteMoviesDatabase: FavoriteMoviesDatabase

    @Before
    fun createFavoriteMoviesDatabase() {
        mFavoriteMoviesDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            FavoriteMoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mFavoriteMoviesDao = mFavoriteMoviesDatabase.favoriteMoviesDao()
    }

    @After
    fun closeDb() {
        mFavoriteMoviesDatabase.close()
    }

    @Test
    fun insertAndGetMovieByIdTest() {
        // Insert a movie
        mFavoriteMoviesDao.insertFavoriteMovie(MOVIES[0]).blockingAwait()

        // Get By Id
        mFavoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertValue(MOVIES[0])
    }

    @Test
    fun insertAndGetMoviesTest() {
        // Insert movies
        mFavoriteMoviesDao.insertFavoriteMovies(MOVIES).blockingAwait()

        // Get all of them
        mFavoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue(MOVIES)
    }

    @Test
    fun deleteAndGetMovieTest() {
        // Insert
        mFavoriteMoviesDao.insertFavoriteMovie(MOVIES[0]).blockingAwait()

        // Delete
        mFavoriteMoviesDao.deleteFavoriteMovie(MOVIES[0]).blockingAwait()

        // Check if it still exists
        mFavoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertNoValues()
    }

    @Test
    fun deleteAndGetMoviesTest() {
        // Insert 5 movies
        mFavoriteMoviesDao.insertFavoriteMovies(MOVIES).blockingAwait()

        // Delete only 2 of them
        mFavoriteMoviesDao.deleteFavoriteMovies(listOf(MOVIES[0], MOVIES[3])).blockingAwait()

        // Check deletions
        mFavoriteMoviesDao.getFavoriteMovieById(MOVIES[0].id)
            .test()
            .assertNoValues()
        mFavoriteMoviesDao.getFavoriteMovieById(MOVIES[3].id)
            .test()
            .assertNoValues()

        // Check if other three movies still exist
        mFavoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue(object : Predicate<List<Movie>> {
                override fun test(movies: List<Movie>): Boolean {
                    return movies.containsAll(listOf(MOVIES[1], MOVIES[2], MOVIES[4]))
                }
            })
    }
}