package com.seungleekim.android.movie.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Movie

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE_DETAIL)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_detail_container, MovieDetailsFragment.newInstance(movie))
                .commit()
        }
    }

    companion object {
        private const val EXTRA_MOVIE_DETAIL = "movie_detail"

        fun newIntent(context: Context?, movie: Movie): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_DETAIL, movie)
            }
        }
    }
}
