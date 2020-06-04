package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trailer(
    val id: String,
    val name: String,
    val key: String,
    val site: String
) : Parcelable

fun Trailer.getThumbnailUrl(): String {
    return if (site.equals("Youtube", ignoreCase = true)) {
        "https://img.youtube.com/vi/$key/0.jpg"
    } else {
        ""
    }
}

fun Trailer.getYoutubeUrl(): String {
    return if (site.equals("Youtube", ignoreCase = true)) {
        "http://www.youtube.com/watch?v=$key"
    } else {
        ""
    }
}