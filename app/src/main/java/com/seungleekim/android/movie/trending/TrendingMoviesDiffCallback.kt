package com.seungleekim.android.movie.trending

import android.support.v7.util.DiffUtil
import com.seungleekim.android.movie.model.Movie

class TrendingMoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(m0: Movie, m1: Movie): Boolean {
        return false
    }

    override fun areContentsTheSame(m0: Movie, m1: Movie): Boolean {
        return false
    }
}
