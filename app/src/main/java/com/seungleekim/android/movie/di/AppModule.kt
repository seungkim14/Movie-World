package com.seungleekim.android.movie.di

import android.content.Context
import com.seungleekim.android.movie.MovieWorldApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: MovieWorldApplication): Context {
        return application.applicationContext
    }
}
