package com.seungleekim.android.movie.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.ui.details.MovieDetailsActivity
import com.seungleekim.android.movie.util.NetworkUtils
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesFragment @Inject constructor() : DaggerFragment(), TrendingMoviesContract.View,
    TrendingMoviesAdapter.OnClickListener {

    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var mPresenter: TrendingMoviesContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.takeView(this)
        mPresenter.loadTrendingMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    private fun initRecyclerView() {
        rv_trending_movies.setHasFixedSize(true)
        rv_trending_movies.layoutManager = GridLayoutManager(context, 2)
        rv_trending_movies.adapter = TrendingMoviesAdapter(this)
    }

    override fun showTrendingMovies(movies: List<Movie>?) {
        (rv_trending_movies.adapter as TrendingMoviesAdapter).submitList(movies)
    }

    override fun showFailureMessage() {
        val errorMessage = when (networkUtils.hasNetworkConnection()) {
            true -> getString(R.string.cannot_load_trending_movies_check_network_connection)
            false -> getString(R.string.cannot_load_trending_movies)
        }
        Snackbar.make(rv_trending_movies, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun showMovieDetail(movie: Movie) {
        val intent = MovieDetailsActivity.newIntent(context, movie)
        startActivity(intent)
    }

    override fun onMovieClicked(movie: Movie) {
        showMovieDetail(movie)
    }

    companion object {
        fun newInstance() = TrendingMoviesFragment()
    }
}
