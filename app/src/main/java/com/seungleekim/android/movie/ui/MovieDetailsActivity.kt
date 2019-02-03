package com.seungleekim.android.movie.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.ui.details.MovieDetailsFragment
import dagger.android.support.DaggerAppCompatActivity

class MovieDetailsActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getParcelableExtra<TrendingMovie>(EXTRA_MOVIE_DETAIL)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_detail_container,
                    MovieDetailsFragment.newInstance(movie)
                )
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_MOVIE_DETAIL = "movie_detail"

        fun newIntent(context: Context?, trendingMovie: TrendingMovie): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_DETAIL, trendingMovie)
            }
        }
    }
}
