package com.seungleekim.android.movie.ui.details

import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.di.FragmentScoped
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