package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.RxSchedulerOverrideRule
import com.seungleekim.android.movie.model.Cast
import com.seungleekim.android.movie.model.Crew
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Review
import com.seungleekim.android.movie.model.Trailer
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.room.FavoriteMoviesDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class MovieDetailsPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulerRule = RxSchedulerOverrideRule()
    }

    @Mock
    private lateinit var tmdbApi: TmdbApi

    @Mock
    private lateinit var view: MovieDetailsContract.View

    @Mock
    private lateinit var favoriteMoviesDao: FavoriteMoviesDao

    private lateinit var movieDetailsPresenter: MovieDetailsPresenter

    private val MOVIE = Movie(1, "title1", "posterPath1", "releaseDate1")
    private val MOVIE_DETAILS = MovieDetails(
        id = 1,
        title = "title1",
        backdropPath = "backdropPath1",
        rating = 1.0,
        mpaaRating = "mpaaRating1",
        runtime = 1,
        genreIds = listOf(28),
        releaseDate = "releaseDate1",
        trailers = listOf(Trailer("id1", "title1", "key1", "site1")),
        overview = "overview",
        crews = listOf(Crew(1, "name1", "department1", "job1")),
        casts = listOf(Cast(1, 1, "name1", "character1")),
        reviews =  listOf(Review("1", "1", "content1"))
    )

    @Before
    fun setupMovieDetailsPresenter() {
        MockitoAnnotations.initMocks(this)
        movieDetailsPresenter = MovieDetailsPresenter(tmdbApi, favoriteMoviesDao)
        movieDetailsPresenter.takeView(view)
    }

    @Test
    fun onGetMovieDetailsSuccess_showMovieDetails() {
        whenever(tmdbApi.getMovieDetail(MOVIE.id)).thenReturn(Single.just(MOVIE_DETAILS))
        movieDetailsPresenter.getMovieDetails(MOVIE)

        verify(view).showMovieDetails(MOVIE_DETAILS)
    }

    @Test
    fun onGetMovieDetailsFailure_showFailureMessage() {
        whenever(tmdbApi.getMovieDetail(MOVIE.id)).thenReturn(Single.error(Exception()))
        movieDetailsPresenter.getMovieDetails(MOVIE)

        verify(view).showFailureMessage(anyString())
    }

    @Test
    fun onGetFavoriteSuccess_showFavorite() {
        whenever(favoriteMoviesDao.getFavoriteMovieById(MOVIE.id)).thenReturn(Maybe.just(MOVIE))
        movieDetailsPresenter.getFavorite(MOVIE)

        verify(view).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onGetFavoriteFailure_showFailureMessage() {
        whenever(favoriteMoviesDao.getFavoriteMovieById(anyInt())).thenReturn(Maybe.error(Exception()))
        movieDetailsPresenter.getFavorite(MOVIE)

        verify(view).showFailureMessage(anyString())
    }

    @Test
    fun onInsertFavoriteMovieSuccess_showFavorite() {
        whenever(favoriteMoviesDao.insertFavoriteMovie(MOVIE)).thenReturn(Completable.complete())
        movieDetailsPresenter.insertFavoriteMovie(MOVIE)

        verify(view).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onInsertFavoriteMovieFailure_showFailureMessage() {
        whenever(favoriteMoviesDao.insertFavoriteMovie(MOVIE)).thenReturn(Completable.error(Exception()))
        movieDetailsPresenter.insertFavoriteMovie(MOVIE)

        verify(view).showFailureMessage(anyString())
    }

    @Test
    fun onDeleteFavoriteMovieSuccess_showFavorite() {
        whenever(favoriteMoviesDao.deleteFavoriteMovie(MOVIE)).thenReturn(Completable.complete())
        movieDetailsPresenter.deleteFavoriteMovie(MOVIE)

        verify(view).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onDeleteFavoriteMovieFailure_showFailureMessage() {
        whenever(favoriteMoviesDao.deleteFavoriteMovie(MOVIE)).thenReturn(Completable.error(Exception()))
        movieDetailsPresenter.deleteFavoriteMovie(MOVIE)

        verify(view).showFailureMessage(anyString())
    }

}