package com.seungleekim.android.movie.ui.details

import android.content.Context
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.di.FragmentScoped
import com.seungleekim.android.movie.persistence.FavoriteMoviesDao
import com.seungleekim.android.movie.persistence.FavoriteMoviesStore
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieDetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun movieDetailsFragment(): MovieDetailsFragment

    @ActivityScoped
    @Binds
    abstract fun movieDetailsPresenter(presenter: MovieDetailsPresenter): MovieDetailsContract.Presenter
}