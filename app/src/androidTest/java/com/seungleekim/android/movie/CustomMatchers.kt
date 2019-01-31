package com.seungleekim.android.movie

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class CustomMatchers {

    companion object {

        /**
         * Copied from: https://github.com/googlecodelabs/android-testing/blob/master/app/src/androidTest/java/com/example/android/testing/notes/custom/matcher/CustomMatchers.java
         *
         * A custom Matcher for Espresso that checks if an ImageView has a drawable applied
         * to it.
         */
        fun hasDrawable(): BoundedMatcher<View, ImageView> {

            return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has drawable")
                }

                override fun matchesSafely(imageView: ImageView): Boolean {
                    return imageView.drawable != null
                }
            }
        }

        /**
         * A custom Matcher for avoiding AmbiguousViewMatcherException
         */
        fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                var currentIndex = 0

                override fun describeTo(description: Description) {
                    description.appendText("with index: ")
                    description.appendValue(index)
                    matcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    return matcher.matches(view) && currentIndex++ == index
                }
            }
        }
    }
}
