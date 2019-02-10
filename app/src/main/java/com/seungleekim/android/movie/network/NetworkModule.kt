package com.seungleekim.android.movie.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.seungleekim.android.movie.model.MovieDetails
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "http://api.themoviedb.org/"

    @Provides
    @Singleton
    fun provideTmdbApiKeyInterceptor() = TmdbApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: TmdbApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(MovieDetails::class.java,
                MovieDetailsDeserializer()
            )
            .create()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)
}
