package com.seungleekim.android.movie.di

import com.seungleekim.android.movie.ui.trending.TrendingMoviesActivity
import com.seungleekim.android.movie.ui.trending.TrendingMoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TrendingMoviesModule::class])
    internal abstract fun trendingMoviesActivity(): TrendingMoviesActivity
}