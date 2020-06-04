package com.seungleekim.android.movie.ui.details.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Review
import kotlinx.android.synthetic.main.fragment_dialog_review.*

class ReviewDialogFragment : DialogFragment() {

    private lateinit var review: Review

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        review = requireNotNull(arguments?.getParcelable(ARG_REVIEW))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle("Review by ${review.author}")
        tv_review_content.text = review.content
    }

    companion object {
        private const val ARG_REVIEW = "review"

        fun newInstance(review: Review): ReviewDialogFragment {
            val fragment = ReviewDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_REVIEW, review)
            fragment.arguments = args
            return fragment
        }
    }
}