package com.seungleekim.android.movie.trending

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.network.TmdbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_trending.view.*

class TrendingMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingMoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)
        initRecyclerView(view)
//        loadTrendingMovies()
        return view
    }

    private fun initRecyclerView(view: View) {
        view.rv_trending_movies.setHasFixedSize(true)
        view.rv_trending_movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_trending_movies.adapter = TrendingMoviesAdapter()
    }

    private fun loadTrendingMovies(tmdbApi: TmdbApi) {
        tmdbApi.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response -> onFetchMovieSuccess(response.trendingMovies)},
                {e -> onFetchMovieFailure(e)}
            )

    }

    private fun onFetchMovieSuccess(movies: List<Movie>) {
        (rv_trending_movies.adapter as TrendingMoviesAdapter).submitList(movies)
    }

    private fun onFetchMovieFailure(e: Throwable?) {
        Log.e(e?.message, e?.stackTrace.toString())
    }
}

