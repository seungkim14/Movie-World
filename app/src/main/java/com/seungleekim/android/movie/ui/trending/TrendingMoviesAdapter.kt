package com.seungleekim.android.movie.ui.trending

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.seungleekim.android.movie.GlideApp
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Movie
import kotlinx.android.synthetic.main.vh_trending_movie.view.*

class TrendingMoviesAdapter : ListAdapter<Movie, TrendingMoviesAdapter.TrendingMoviesViewHolder>(
    TrendingMoviesDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_trending_movie, parent, false)
        return TrendingMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class TrendingMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, position: Int) = with(itemView) {
            GlideApp.with(context).load(movie.getPosterUrl()).into(iv_trending_movie_poster)
            setOnClickListener {
                Toast.makeText(context, "${movie.title} clicked!", Toast.LENGTH_SHORT).show()
                // TODO: open MovieDetail screen
            }
        }
    }

    class TrendingMoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return oldMovie.id == newMovie.id
        }

        override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return oldMovie.title == newMovie.title
                    && oldMovie.releaseDate == newMovie.releaseDate
        }
    }
}