package com.seungleekim.android.movie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_trending.view.*

class TrendingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)
        val movies = loadPopularMovies()
        initRecyclerView(view, movies)

        return view
    }

    private fun loadPopularMovies(): List<Movie> {
        val POSTER_BASE = "http://image.tmdb.org/t/p/w185//"
        return listOf(
            Movie(297802, "Aquaman", "${POSTER_BASE}5Kg76ldv7VxeX9YlcQXiowHgdX6.jpg", 6.9),
            Movie(424783, "Bumblebee", "${POSTER_BASE}fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg", 6.6),
            Movie(400650, "Mary Poppins Returns", "${POSTER_BASE}uTVGku4LibMGyKgQvjBtv3OYfAX.jpg", 6.8))
    }

    private fun initRecyclerView(view: View, movies: List<Movie>) {
        view.rv_trending_movies.setHasFixedSize(true)
        view.rv_trending_movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_trending_movies.adapter = TrendingAdapter()
        (view.rv_trending_movies.adapter as TrendingAdapter).submitList(movies)
    }
}
