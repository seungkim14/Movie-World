package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.dagger.ActivityScoped
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.network.response.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesPresenter @Inject constructor(
    private val tmdbApi: TmdbApi
) : TrendingMoviesContract.Presenter {

    private var view: TrendingMoviesContract.View? = null
    private val disposables = CompositeDisposable()

    override fun loadTrendingMovies() {
        disposables.add(tmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onGetMoviesSuccess, this::onGetMoviesFailure))
    }

    override fun onGetMoviesSuccess(response: MoviesResponse?) {
        view?.showTrendingMovies(response?.movies)
    }

    override fun onGetMoviesFailure(e: Throwable?) {
        Timber.d(e)
        view?.showFailureMessage()
    }

    override fun takeView(view: TrendingMoviesContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
        disposables.clear()
    }
}
