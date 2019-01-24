package com.seungleekim.android.movie.ui.trending

import android.util.Log
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesPresenter @Inject constructor() :
    TrendingMoviesContract.Presenter {

    private var mView: TrendingMoviesContract.View? = null

    @Inject
    lateinit var tmdbApi: TmdbApi

    override fun getTrendingMovies() {
        tmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response -> onGetMoviesSuccess(response)},
                {e -> onGetMoviesFailure(e)}
            )
    }

    private fun onGetMoviesSuccess(response: MoviesResponse?) {
        mView?.showTrendingMovies(response?.trendingMovies)
    }

    private fun onGetMoviesFailure(e: Throwable?) {
        Log.e(e?.message, e?.stackTrace.toString())
    }

    override fun takeView(view: TrendingMoviesContract.View) {
        mView = view
        getTrendingMovies()
    }

    override fun dropView() {
        mView = null
    }

}
