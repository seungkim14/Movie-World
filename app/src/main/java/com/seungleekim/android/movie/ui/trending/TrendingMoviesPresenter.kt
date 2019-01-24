package com.seungleekim.android.movie.ui.trending

import android.util.Log
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import com.seungleekim.android.movie.util.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesPresenter @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val networkUtils: NetworkUtils
) : TrendingMoviesContract.Presenter {

    private var mView: TrendingMoviesContract.View? = null
    private var mGetMovieDisposable: Disposable? = null

    override fun getTrendingMovies() {
        mGetMovieDisposable = tmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> onGetMoviesSuccess(response) },
                { e -> onGetMoviesFailure(e) }
            )
    }

    private fun onGetMoviesSuccess(response: MoviesResponse?) {
        mView?.showTrendingMovies(response?.trendingMovies)
    }

    private fun onGetMoviesFailure(e: Throwable?) {
        Log.e(e?.message, e?.stackTrace.toString())
        var message = "Cannot load trending movies"
        if (!networkUtils.hasNetworkConnection()) {
            message = "Cannot load trending movies: Check network connection"
        }
        mView?.showFailureMessage(message)
    }

    override fun takeView(view: TrendingMoviesContract.View) {
        mView = view
        getTrendingMovies()
    }

    override fun dropView() {
        mView = null
        mGetMovieDisposable?.dispose()
    }
}
