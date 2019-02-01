package com.seungleekim.android.movie.di

import com.seungleekim.android.movie.ui.MainActivity
import com.seungleekim.android.movie.ui.details.MovieDetailsActivity
import com.seungleekim.android.movie.ui.details.MovieDetailsModule
import com.seungleekim.android.movie.ui.trending.TrendingMoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TrendingMoviesModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    internal abstract fun movieDetailsActivity(): MovieDetailsActivity
}
