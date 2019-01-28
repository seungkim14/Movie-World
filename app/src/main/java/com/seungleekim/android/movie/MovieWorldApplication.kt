package com.seungleekim.android.movie

import com.seungleekim.android.movie.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

class MovieWorldApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initCalligraphy()
    }

    private fun initCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Raleway-Medium.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                ).build()
        )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
