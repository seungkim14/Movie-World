package com.seungleekim.android.movie.dagger

import com.seungleekim.android.movie.MovieWorldApplication
import com.seungleekim.android.movie.network.NetworkModule
import com.seungleekim.android.movie.room.PersistenceModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PersistenceModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<MovieWorldApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MovieWorldApplication>()
}
