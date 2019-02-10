package com.seungleekim.android.movie.ui.details.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Review
import kotlinx.android.synthetic.main.vh_movie_review.view.*

class MovieReviewsAdapter(
    val onClickListener: OnClickListener
) : ListAdapter<Review, MovieReviewsAdapter.MovieReviewViewHolder>(
    MovieReviewsDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_movie_review, parent, false)
        return MovieReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class MovieReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(review: Review, position: Int) = with(itemView) {
            tv_movie_review_author.text = "Review by: ${review.author}"
            tv_movie_review_content.text = review.content
            setOnClickListener {
                onClickListener.onReviewClick(review)
            }
        }
    }

    class MovieReviewsDiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldReview: Review, newReview: Review): Boolean {
            return oldReview.id == newReview.id
        }

        override fun areContentsTheSame(oldReview: Review, newReview: Review): Boolean {
            return oldReview.author == newReview.author && oldReview.content == newReview.content
        }
    }

    interface OnClickListener {
        fun onReviewClick(review: Review)
    }
}
