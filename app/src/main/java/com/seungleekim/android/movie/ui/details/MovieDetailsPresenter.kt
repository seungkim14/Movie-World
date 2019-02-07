package com.seungleekim.android.movie.ui.details

import android.annotation.SuppressLint
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.network.TmdbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class MovieDetailsPresenter @Inject constructor(
    private val tmdbApi: TmdbApi
) : MovieDetailsContract.Presenter {

    private var mView: MovieDetailsContract.View? = null

    @SuppressLint("CheckResult")
    override fun getMovieDetails(trendingMovie: TrendingMovie) {
        tmdbApi.getMovieDetail(trendingMovie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> onGetMovieDetailsSuccess(response) },
                { e -> onGetMovieDetailsFailure(e) }
            )
    }

    override fun getFavorite(trendingMovie: TrendingMovie) {
    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetails) {
        mView?.showMovieDetails(movieDetails)
    }

    private fun onGetMovieDetailsFailure(e: Throwable?) {
        Timber.d(e, "onGetMovieDetailsFailure()")
    }


    override fun setFavorite(b: Boolean) {
    }


    override fun takeView(view: MovieDetailsContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
    }
}