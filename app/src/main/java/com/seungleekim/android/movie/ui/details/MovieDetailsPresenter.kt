package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.dagger.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.network.TmdbApi
import com.seungleekim.android.movie.room.FavoriteMoviesDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class MovieDetailsPresenter @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val favoriteMoviesDao: FavoriteMoviesDao
) : MovieDetailsContract.Presenter {

    private var view: MovieDetailsContract.View? = null
    private val disposables = CompositeDisposable()

    override fun getMovieDetails(movie: Movie) {
        disposables.add(tmdbApi.getMovieDetail(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieDetails -> onGetMovieDetailsSuccess(movieDetails)},
                { e -> showFailureMessage(e, "Failed to get movie details") }
            )
        )
    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetails) {
        view?.showMovieDetails(movieDetails)
    }

    override fun getFavorite(movie: Movie) {
        disposables.add(favoriteMoviesDao.getFavoriteMovieById(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setFavorite(setFavorite = true) },
                { e -> showFailureMessage(e, "Failed to search local database") }
            )
        )
    }

    override fun onFavoriteFabClick(movie: Movie) {
        disposables.add(favoriteMoviesDao.getFavoriteMovieById(movie.id)
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
        disposables.add(favoriteMoviesDao.insertFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setFavorite(setFavorite = true, buttonClicked = true) },
                { e -> showFailureMessage(e, "Failed to insert favorite movie") }
            )
        )
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        disposables.add(favoriteMoviesDao.deleteFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ setFavorite(setFavorite = false) },
                { e -> showFailureMessage(e, "Failed to delete favorite movie") }
            )
        )
    }

    private fun showFailureMessage(e: Throwable, message: String) {
        Timber.d(e, "showFaliureMessage()")
        view?.showFailureMessage(message)
    }

    override fun setFavorite(setFavorite: Boolean, buttonClicked: Boolean) {
        view?.showFavorite(setFavorite, buttonClicked)
    }

    override fun takeView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
        disposables.clear()
    }
}