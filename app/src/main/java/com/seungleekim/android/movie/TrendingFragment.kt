package com.seungleekim.android.movie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_trending.view.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TrendingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)
        initRecyclerView(view)
        loadTrendingMovies()
        return view
    }

    private fun initRecyclerView(view: View) {
        view.rv_trending_movies.setHasFixedSize(true)
        view.rv_trending_movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_trending_movies.adapter = TrendingAdapter()
    }

    private fun loadTrendingMovies() {
        val BASE_URL = "http://api.themoviedb.org/"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val tmdbApi = retrofit.create(TmdbApi::class.java)

        val trendingMoviesResponse = tmdbApi.getTrendingMovies()

        trendingMoviesResponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response -> onFetchMovieSuccess(response.trendingMovies)},
                {e -> onFetchMovieFailure(e)}
            )

    }

    private fun onFetchMovieSuccess(movies: List<Movie>) {
        (rv_trending_movies.adapter as TrendingAdapter).submitList(movies)
    }

    private fun onFetchMovieFailure(e: Throwable?) {
        Log.e(e?.message, e?.stackTrace.toString())
    }
}

