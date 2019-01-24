package com.seungleekim.android.movie.di

import com.seungleekim.android.movie.MovieWorldApplication
import com.seungleekim.android.movie.network.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<MovieWorldApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MovieWorldApplication>()
}