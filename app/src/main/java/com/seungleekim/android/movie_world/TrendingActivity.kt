package com.seungleekim.android.movie_world

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        initTrendingFragment(savedInstanceState)
    }

    private fun initTrendingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, TrendingFragment())
                .commit()
        }
    }
}
