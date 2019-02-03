package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesPresenter @Inject constructor(
    private val tmdbApi: TmdbApi
) : TrendingMoviesContract.Presenter {

    private var mView: TrendingMoviesContract.View? = null
    private var mGetMoviesDisposable: Disposable? = null

    override fun loadTrendingMovies() {
        mGetMoviesDisposable = tmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> onGetMoviesSuccess(response) },
                { e -> onGetMoviesFailure(e) }
            )
    }

    override fun onGetMoviesSuccess(response: MoviesResponse?) {
        mView?.showTrendingMovies(response?.trendingMovies)
    }

    override fun onGetMoviesFailure(e: Throwable?) {
        mView?.showFailureMessage()
    }

    override fun takeView(view: TrendingMoviesContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
        mGetMoviesDisposable?.dispose()
    }
}
