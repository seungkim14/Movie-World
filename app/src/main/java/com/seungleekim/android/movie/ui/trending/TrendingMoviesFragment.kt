package com.seungleekim.android.movie.ui.trending

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesFragment @Inject constructor() : DaggerFragment(), TrendingMoviesContract.View {

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
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    private fun initRecyclerView() {
        rv_trending_movies.setHasFixedSize(true)
        rv_trending_movies.layoutManager = GridLayoutManager(context, 2)
        rv_trending_movies.adapter = TrendingMoviesAdapter()
    }

    override fun showTrendingMovies(movies: List<Movie>?) {
        (rv_trending_movies.adapter as TrendingMoviesAdapter).submitList(movies)
    }

    override fun showFailureMessage(errorMessage: String) {
        Snackbar.make(trending_container, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    companion object {
        fun newInstance() = TrendingMoviesFragment()
    }
}
