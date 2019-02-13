package com.seungleekim.android.movie.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.seungleekim.android.movie.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UINavigationTest {

    @Rule
    @JvmField
    val movieActivityTestRule = ActivityTestRule<MovieActivity>(MovieActivity::class.java)

    @Test
    fun bottomNavigation_clickTrendingMovies_opensTrendingMoviesScreen() {
        // Click trending movies tab
        onView(withId(R.id.bottom_navigation_trending)).perform(click())
        // Check that trending movies fragment was opened
        onView(withId(R.id.fragment_container_trending)).check(matches(isDisplayed()))
    }

    @Test
    fun bottomNavigation_clickSearch_opensSearchMoviesScreen() {
        // Click search movies tab
        onView(withId(R.id.bottom_navigation_search)).perform(click())
        // Check that search movies fragment was opened
        onView(withId(R.id.fragment_container_search)).check(matches(isDisplayed()))
    }

    @Test
    fun bottomNavigation_clickFavorites_opensFavoriteMoviesScreen() {
        // Click favorite movies tab
        onView(withId(R.id.bottom_navigation_favorites)).perform(click())
        // Check that favorite movies fragment was opened
        onView(withId(R.id.fragment_container_favorites)).check(matches(isDisplayed()))
    }
}