package com.seungleekim.android.movie.ui.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seungleekim.android.movie.util.GlideApp
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.TrendingMovie
import kotlinx.android.synthetic.main.vh_trending_movie.view.*

class TrendingMoviesAdapter(
    val onClickListener: OnClickListener
) : ListAdapter<TrendingMovie, TrendingMoviesAdapter.TrendingMoviesViewHolder>(
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
        fun bind(trendingMovie: TrendingMovie, position: Int) = with(itemView) {
            GlideApp.with(context).load(trendingMovie.getPosterUrl()).into(iv_trending_movie_poster)
            setOnClickListener {
                onClickListener.onMovieClicked(trendingMovie)
            }
        }
    }

    class TrendingMoviesDiffCallback : DiffUtil.ItemCallback<TrendingMovie>() {
        override fun areItemsTheSame(oldTrendingMovie: TrendingMovie, newTrendingMovie: TrendingMovie): Boolean {
            return oldTrendingMovie.id == newTrendingMovie.id
        }

        override fun areContentsTheSame(oldTrendingMovie: TrendingMovie, newTrendingMovie: TrendingMovie): Boolean {
            return oldTrendingMovie.title == newTrendingMovie.title
                    && oldTrendingMovie.releaseDate == newTrendingMovie.releaseDate
        }
    }

    interface OnClickListener {
        fun onMovieClicked(trendingMovie: TrendingMovie)
    }
}
