package com.seungleekim.android.movie.dagger

import android.content.Context
import com.seungleekim.android.movie.MovieWorldApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: MovieWorldApplication): Context {
        return application.applicationContext
    }
}
