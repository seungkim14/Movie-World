package com.seungleekim.android.movie.ui.trending

import android.os.Bundle
import com.seungleekim.android.movie.R
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TrendingMoviesActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mFragmentProvider: Lazy<TrendingMoviesFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        initTrendingMoviesFragment()
    }

    private fun initTrendingMoviesFragment() {
        var trendingMoviesFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container)

        if (trendingMoviesFragment == null) {
            trendingMoviesFragment = mFragmentProvider.get()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, trendingMoviesFragment)
                .commit()
        }
    }
}
