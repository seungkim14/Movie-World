package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.RxSchedulerOverrideRule
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TrendingMoviesPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulerRule = RxSchedulerOverrideRule()
    }

    private lateinit var moviesResponse: MoviesResponse

    @Mock
    private lateinit var tmdbApi: TmdbApi

    @Mock
    private lateinit var mTrendingMoviesView: TrendingMoviesContract.View

    private lateinit var mTrendingMoviesPresenter: TrendingMoviesPresenter

    @Before
    fun setupTrendingMoviesPresenter() {
        MockitoAnnotations.initMocks(this)

        // Create moviesResponse
        moviesResponse = MoviesResponse()
        moviesResponse.movies = listOf(
            Movie(1, "firstTitle", "firstPosterPath", 1.0, "firstReleaseDate"),
            Movie(2, "secondTitle", "secondPosterPath", 2.0, "secondReleaseDate")
        )

        mTrendingMoviesPresenter = TrendingMoviesPresenter(tmdbApi)
        mTrendingMoviesPresenter.takeView(mTrendingMoviesView)
    }

    @Test
    fun onGetMovieSuccess_ShowTrendingMovies() {
        mTrendingMoviesPresenter.onGetMoviesSuccess(moviesResponse)
        verify(mTrendingMoviesView).showTrendingMovies(moviesResponse.movies)
    }

    @Test
    fun onGetMovieFailure_ShowFailureMessage() {
        mTrendingMoviesPresenter.onGetMoviesFailure(Exception())
        verify(mTrendingMoviesView).showFailureMessage()
    }
}