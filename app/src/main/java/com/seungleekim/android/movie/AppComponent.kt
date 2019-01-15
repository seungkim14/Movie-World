package com.seungleekim.android.movie

import com.seungleekim.android.movie.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

}