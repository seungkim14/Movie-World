package com.seungleekim.android.movie.trending

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.seungleekim.android.movie.GlideApp
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Movie
import kotlinx.android.synthetic.main.vh_trending_movie.view.*

class TrendingMoviesAdapter : ListAdapter<Movie, TrendingMoviesAdapter.TrendingViewHolder>(
    TrendingMoviesDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_trending_movie, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, position: Int) = with(itemView) {
            GlideApp.with(context).load(movie.getPosterUrl()).into(iv_trending_movie_poster)
            tv_trending_movie_title.text = movie.title
            tv_trending_movie_rank.text = (position + 1).toString()
            tv_trending_movie_rating.text = movie.rating.toString()

            setOnClickListener {
                Toast.makeText(context, "${movie.title} clicked!", Toast.LENGTH_SHORT).show()
                // TODO: open MovieDetail screen
            }
        }
    }
}