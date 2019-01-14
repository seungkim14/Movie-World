package com.seungleekim.android.movie

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.vh_trending_movie.view.*

class TrendingAdapter : ListAdapter<Movie, TrendingAdapter.TrendingViewHolder>(TrendingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_trending_movie, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingAdapter.TrendingViewHolder, position: Int) {
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

class TrendingDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(m0: Movie, m1: Movie): Boolean {
        return false
    }

    override fun areContentsTheSame(m0: Movie, m1: Movie): Boolean {
        return false
    }
}

