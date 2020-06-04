package com.seungleekim.android.movie.ui.trending

import com.seungleekim.android.movie.dagger.ActivityScoped
import com.seungleekim.android.movie.dagger.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrendingMoviesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun trendingMoviesFragment(): TrendingMoviesFragment

    @ActivityScoped
    @Binds
    abstract fun trendingMoviesPresenter(presenter: TrendingMoviesPresenter): TrendingMoviesContract.Presenter
}
