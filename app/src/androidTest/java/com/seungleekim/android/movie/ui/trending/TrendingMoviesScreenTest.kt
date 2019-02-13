package com.seungleekim.android.movie.ui.trending

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.seungleekim.android.movie.ui.CustomMatchers.Companion.hasDrawable
import com.seungleekim.android.movie.ui.CustomMatchers.Companion.withIndex
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.Movie
import com.seungleekim.android.movie.ui.MovieActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TrendingMoviesScreenTest {

    @Rule
    @JvmField
    val mainActivityTestRule = ActivityTestRule<MovieActivity>(MovieActivity::class.java)

    // Too lazy to make mock product flavor...
    val FAKE_TRENDING_MOVIES: MutableList<Movie> = mutableListOf()

    @Before
    fun populateFakeMoviesIntoUi() {
        // Populate trending movies screen with fake movies
        for (i in 1..5) {
            FAKE_TRENDING_MOVIES.add(Movie(i, "title{$i}", "file:///android_asset/test-image.png", "releaseDate{$i}"))
        }
        ((mainActivityTestRule.activity as MovieActivity).supportFragmentManager.findFragmentByTag(MovieActivity.TAG_TRENDING_FRAGMENT)
                as TrendingMoviesFragment).showTrendingMovies(FAKE_TRENDING_MOVIES)
    }

    @Test
    fun trendingMovies_DisplayedInUi() {
        onView(withId(R.id.rv_trending_movies)).check(matches(isDisplayed()))
        // Scroll to the trending movie at index i
        onView(withId(R.id.rv_trending_movies)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(0))
        // Check if the trending movie at index i has the poster (a drawable)
        onView(withIndex(withId(R.id.iv_trending_movie_poster), 0)).check(matches(allOf(hasDrawable(), isDisplayed())))
    }

    @Test
    fun clickMovie_opensMovieDetailScreen() {
        // Verify if movie detail screen is displayed when a trending movie is clicked
        onView(withId(R.id.rv_trending_movies)).check(matches(isDisplayed()))
        // Scroll to the trending movie at index i
        onView(withId(R.id.rv_trending_movies)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(0))
        // Click the trending movie at index i
        onView(withId(R.id.rv_trending_movies)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // Check if the movie detail fragment is displayed
        onView(withId(R.id.fragment_container_details)).check(matches(isDisplayed()))
    }
}
