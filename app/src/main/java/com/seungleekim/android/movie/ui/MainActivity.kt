package com.seungleekim.android.movie.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.ui.favorites.FavoriteMoviesFragment
import com.seungleekim.android.movie.ui.search.SearchMoviesFragment
import com.seungleekim.android.movie.ui.trending.TrendingMoviesFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragmentByTag(TAG_TRENDING_FRAGMENT)
        initBottomNavigationView()
    }

    private fun initFragmentByTag(tag: String) {
        val fragment = findFragmentByTag(tag)
        replaceFragment(fragment, tag)
    }

    private fun findFragmentByTag(tag: String): Fragment {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            return fragment
        }
        return when (tag) {
            TAG_TRENDING_FRAGMENT -> TrendingMoviesFragment.newInstance()
            TAG_SEARCH_FRAGMENT -> SearchMoviesFragment.newInstance()
            else -> FavoriteMoviesFragment.newInstance()
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        if (tag == currentFragmentTag) {
            return
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null)
            .commit()
        currentFragmentTag = tag
    }

    private fun initBottomNavigationView() {
        view_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_trending -> { initFragmentByTag(TAG_TRENDING_FRAGMENT) }
                R.id.bottom_navigation_search -> { initFragmentByTag(TAG_SEARCH_FRAGMENT) }
                R.id.bottom_navigation_favorites -> { initFragmentByTag(TAG_FAVORITES_FRAGMENT) }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    companion object {
        const val TAG_TRENDING_FRAGMENT = "trending_fragment"
        const val TAG_SEARCH_FRAGMENT = "search_fragment"
        const val TAG_FAVORITES_FRAGMENT = "favorites_fragment"
    }
}
