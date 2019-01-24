package com.seungleekim.android.movie.ui.trending

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.Movie
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_trending.view.*
import javax.inject.Inject

@ActivityScoped
class TrendingMoviesFragment @Inject constructor() : DaggerFragment(), TrendingMoviesContract.View {

    @Inject
    lateinit var mPresenter: TrendingMoviesContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)
        initRecyclerView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    private fun initRecyclerView(view: View) {
        view.rv_trending_movies.setHasFixedSize(true)
        view.rv_trending_movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_trending_movies.adapter = TrendingMoviesAdapter()
    }

    override fun showTrendingMovies(movies: List<Movie>?) {
        (rv_trending_movies.adapter as TrendingMoviesAdapter).submitList(movies)
    }

    companion object {
        fun newInstance() = TrendingMoviesFragment()
    }
}
