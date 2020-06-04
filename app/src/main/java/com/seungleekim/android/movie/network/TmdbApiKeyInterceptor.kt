package com.seungleekim.android.movie.network

import okhttp3.Interceptor
import okhttp3.Response

// yolo
private const val API_KEY = "9d09208e1c09792a5dae8a6c6a749a28"

class TmdbApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
