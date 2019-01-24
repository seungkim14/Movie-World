package com.seungleekim.android.movie.ui

interface BaseContract {

    interface View<P>

    interface Presenter<V> {
        fun takeView(view: V)
        fun dropView()
    }
}