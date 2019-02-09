package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.persistence.FavoriteMoviesStore
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.maybe.MaybeObserveOn
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class MovieDetailsPresenter @Inject constructor(
    private val mTmdbApi: TmdbApi,
    private val mFavoriteMoviesStore: FavoriteMoviesStore
) : MovieDetailsContract.Presenter {

    private var mView: MovieDetailsContract.View? = null
    private val mDisposables = CompositeDisposable()

    override fun getMovieDetails(movie: Movie) {
        mDisposables.add(mTmdbApi.getMovieDetail(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onGetMovieDetailsSuccess, this::onGetMovieDetailsFailure))
    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetails) {
        mView?.showMovieDetails(movieDetails)
    }

    private fun onGetMovieDetailsFailure(e: Throwable?) {
        Timber.d(e, "onGetMovieDetailsFailure()")
    }

    override fun getFavorite(movie: Movie) {
        mDisposables.add(mFavoriteMoviesStore.getFavoriteMovieById(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { setFavorite(true) }, { e -> showFailureMessage(e) })
        )
    }

    override fun onFavoriteFabClick(movie: Movie) {
        mDisposables.add(mFavoriteMoviesStore.getFavoriteMovieById(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableMaybeObserver<Movie>() {
                override fun onSuccess(movie: Movie) {
                    Timber.d("Deleted Favorite Movie ${movie.title}")
                    deleteFavoriteMovie(movie)
                }

                override fun onComplete() {
                    Timber.d("Inserted Favorite Movie ${movie.title}")
                    insertFavoriteMovie(movie)
                }

                override fun onError(ignored: Throwable) {}
            })
        )
    }

    override fun insertFavoriteMovie(movie: Movie) {
        mDisposables.add(mFavoriteMoviesStore.insertFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { setFavorite(true) }, { e -> showFailureMessage(e) })
        )
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        mDisposables.add(mFavoriteMoviesStore.deleteFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { setFavorite(false) }, { e -> showFailureMessage(e) })
        )
    }

    private fun showFailureMessage(e: Throwable) {
        Timber.d(e, "showFaliureMessage()")
        mView?.showFailureMessage()
    }

    override fun setFavorite(b: Boolean) {
        mView?.showFavorite(b)
    }

    override fun takeView(view: MovieDetailsContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
        mDisposables.clear()
    }
}