package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.RxSchedulerOverrideRule
import com.seungleekim.android.movie.model.Cast
import com.seungleekim.android.movie.model.Crew
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Review
import com.seungleekim.android.movie.model.Trailer
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.persistence.FavoriteMoviesDao
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
    private lateinit var mTmdbApi: TmdbApi

    @Mock
    private lateinit var mMovieDetailsView: MovieDetailsContract.View

    @Mock
    private lateinit var mFavoriteMoviesDao: FavoriteMoviesDao

    private lateinit var mMovieDetailsPresenter: MovieDetailsPresenter

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
        mMovieDetailsPresenter = MovieDetailsPresenter(mTmdbApi, mFavoriteMoviesDao)
        mMovieDetailsPresenter.takeView(mMovieDetailsView)
    }

    @Test
    fun onGetMovieDetailsSuccess_showMovieDetails() {
        whenever(mTmdbApi.getMovieDetail(MOVIE.id)).thenReturn(Single.just(MOVIE_DETAILS))
        mMovieDetailsPresenter.getMovieDetails(MOVIE)

        verify(mMovieDetailsView).showMovieDetails(MOVIE_DETAILS)
    }

    @Test
    fun onGetMovieDetailsFailure_showFailureMessage() {
        whenever(mTmdbApi.getMovieDetail(MOVIE.id)).thenReturn(Single.error(Exception()))
        mMovieDetailsPresenter.getMovieDetails(MOVIE)

        verify(mMovieDetailsView).showFailureMessage(anyString())
    }

    @Test
    fun onGetFavoriteSuccess_showFavorite() {
        whenever(mFavoriteMoviesDao.getFavoriteMovieById(MOVIE.id)).thenReturn(Maybe.just(MOVIE))
        mMovieDetailsPresenter.getFavorite(MOVIE)

        verify(mMovieDetailsView).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onGetFavoriteFailure_showFailureMessage() {
        whenever(mFavoriteMoviesDao.getFavoriteMovieById(anyInt())).thenReturn(Maybe.error(Exception()))
        mMovieDetailsPresenter.getFavorite(MOVIE)

        verify(mMovieDetailsView).showFailureMessage(anyString())
    }

    @Test
    fun onInsertFavoriteMovieSuccess_showFavorite() {
        whenever(mFavoriteMoviesDao.insertFavoriteMovie(MOVIE)).thenReturn(Completable.complete())
        mMovieDetailsPresenter.insertFavoriteMovie(MOVIE)

        verify(mMovieDetailsView).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onInsertFavoriteMovieFailure_showFailureMessage() {
        whenever(mFavoriteMoviesDao.insertFavoriteMovie(MOVIE)).thenReturn(Completable.error(Exception()))
        mMovieDetailsPresenter.insertFavoriteMovie(MOVIE)

        verify(mMovieDetailsView).showFailureMessage(anyString())
    }

    @Test
    fun onDeleteFavoriteMovieSuccess_showFavorite() {
        whenever(mFavoriteMoviesDao.deleteFavoriteMovie(MOVIE)).thenReturn(Completable.complete())
        mMovieDetailsPresenter.deleteFavoriteMovie(MOVIE)

        verify(mMovieDetailsView).showFavorite(anyBoolean(), anyBoolean())
    }

    @Test
    fun onDeleteFavoriteMovieFailure_showFailureMessage() {
        whenever(mFavoriteMoviesDao.deleteFavoriteMovie(MOVIE)).thenReturn(Completable.error(Exception()))
        mMovieDetailsPresenter.deleteFavoriteMovie(MOVIE)

        verify(mMovieDetailsView).showFailureMessage(anyString())
    }

}