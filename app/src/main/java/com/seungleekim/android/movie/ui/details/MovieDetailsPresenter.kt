package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.persistence.FavoriteMoviesDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class MovieDetailsPresenter @Inject constructor(
    private val mTmdbApi: TmdbApi,
    private val mFavoriteMoviesDao: FavoriteMoviesDao
) : MovieDetailsContract.Presenter {

    private var mView: MovieDetailsContract.View? = null
    private val mDisposables = CompositeDisposable()

    override fun getMovieDetails(movie: Movie) {
        mDisposables.add(mTmdbApi.getMovieDetail(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieDetails -> onGetMovieDetailsSuccess(movieDetails)},
                { e -> showFailureMessage(e, "Failed to get movie details") }
            )
        )
    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetails) {
        mView?.showMovieDetails(movieDetails)
    }

    override fun getFavorite(movie: Movie) {
        mDisposables.add(mFavoriteMoviesDao.getFavoriteMovieById(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setFavorite(setFavorite = true) },
                { e -> showFailureMessage(e, "Failed to search local database") }
            )
        )
    }

    override fun onFavoriteFabClick(movie: Movie) {
        mDisposables.add(mFavoriteMoviesDao.getFavoriteMovieById(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableMaybeObserver<Movie>() {
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
        mDisposables.add(mFavoriteMoviesDao.insertFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setFavorite(setFavorite = true, buttonClicked = true) },
                { e -> showFailureMessage(e, "Failed to insert favorite movie") }
            )
        )
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        mDisposables.add(mFavoriteMoviesDao.deleteFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ setFavorite(setFavorite = false) },
                { e -> showFailureMessage(e, "Failed to delete favorite movie") }
            )
        )
    }

    private fun showFailureMessage(e: Throwable, message: String) {
        Timber.d(e, "showFaliureMessage()")
        mView?.showFailureMessage(message)
    }

    override fun setFavorite(setFavorite: Boolean, buttonClicked: Boolean) {
        mView?.showFavorite(setFavorite, buttonClicked)
    }

    override fun takeView(view: MovieDetailsContract.View) {
        mView = view
    }

    override fun dropView() {
        mView = null
        mDisposables.clear()
    }
}