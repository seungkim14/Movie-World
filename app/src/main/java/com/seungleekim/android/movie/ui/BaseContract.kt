package com.seungleekim.android.movie.ui

import android.view.View

interface BaseContract {

    interface View<P> {
        fun showView(view: android.view.View?) {
            view?.visibility = android.view.View.VISIBLE
        }
        fun hideView(view: android.view.View?) {
            view?.visibility = android.view.View.GONE
        }
    }

    interface Presenter<V> {
        fun takeView(view: V)
        fun dropView()
    }
}
