package com.seungleekim.android.movie

import android.app.Application

class MovieWorldApplication : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}