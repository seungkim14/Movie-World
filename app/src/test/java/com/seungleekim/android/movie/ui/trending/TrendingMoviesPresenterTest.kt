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
    private lateinit var view: TrendingMoviesContract.View

    private lateinit var trendingMoviesPresenter: TrendingMoviesPresenter

    @Before
    fun setupTrendingMoviesPresenter() {
        MockitoAnnotations.initMocks(this)

        // Create moviesResponse
        moviesResponse = MoviesResponse()
        moviesResponse.movies = listOf(
            Movie(1, "firstTitle", "firstPosterPath", "firstReleaseDate"),
            Movie(2, "secondTitle", "secondPosterPath", "secondReleaseDate")
        )

        trendingMoviesPresenter = TrendingMoviesPresenter(tmdbApi)
        trendingMoviesPresenter.takeView(view)
    }

    @Test
    fun onGetMovieSuccess_ShowTrendingMovies() {
        trendingMoviesPresenter.onGetMoviesSuccess(moviesResponse)
        verify(view).showTrendingMovies(moviesResponse.movies)
    }

    @Test
    fun onGetMovieFailure_ShowFailureMessage() {
        trendingMoviesPresenter.onGetMoviesFailure(Exception())
        verify(view).showFailureMessage()
    }
}