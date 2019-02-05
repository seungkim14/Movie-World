package com.seungleekim.android.movie.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Trailer
import com.seungleekim.android.movie.util.GlideApp
import kotlinx.android.synthetic.main.vh_movie_trailer.view.*

class MovieTrailersAdapter(
    val onClickListener: OnClickListener
) : ListAdapter<Trailer, MovieTrailersAdapter.TrendingMoviesViewHolder>(
    MovieTrailersDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_movie_trailer, parent, false)
        return TrendingMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class TrendingMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(trailer: Trailer, position: Int) = with(itemView) {
            tv_movie_details_trailer.text = trailer.name
            GlideApp.with(context).load(trailer.getThumbnailUrl()).into(iv_movie_details_trailer)
            setOnClickListener {
                onClickListener.onTrailerClick(trailer)
            }
        }
    }

    class MovieTrailersDiffCallback : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldTrailer: Trailer, newTrailer: Trailer): Boolean {
            return oldTrailer.id == newTrailer.id
        }

        override fun areContentsTheSame(oldTrailer: Trailer, newTrailer: Trailer): Boolean {
            return oldTrailer.site == newTrailer.site && oldTrailer.key == newTrailer.key
        }
    }

    interface OnClickListener {
        fun onTrailerClick(trailer: Trailer)
    }
}
