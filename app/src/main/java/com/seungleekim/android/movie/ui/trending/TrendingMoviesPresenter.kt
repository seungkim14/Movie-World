package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesPresenter @Inject constructor(
    private val mTmdbApi: TmdbApi
) : TrendingMoviesContract.Presenter {

    private var mView: TrendingMoviesContract.View? = null
    private val mDisposables = CompositeDisposable()

    override fun loadTrendingMovies() {
        mDisposables.add(mTmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onGetMoviesSuccess, this::onGetMoviesFailure))
    }

    override fun onGetMoviesSuccess(response: MoviesResponse?) {
        mView?.showTrendingMovies(response?.movies)
    }

    override fun onGetMoviesFailure(e: Throwable?) {
        Timber.d(e)
        mView?.showFailureMessage()
    }

    override fun takeView(view: TrendingMoviesContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
        mDisposables.clear()
    }
}
