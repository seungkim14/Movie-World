package com.seungleekim.android.movie.trending

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.seungleekim.android.movie.R

class TrendingMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        initTrendingFragment(savedInstanceState)
    }

    private fun initTrendingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    TrendingMoviesFragment.newInstance()
                )
                .commit()
        }
    }
}
