package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Trailer(
    val id: String,
    val name: String,
    val key: String,
    val site: String
) : Parcelable {

    fun getThumbnailUrl(): String {
        return if (site.equals("Youtube", ignoreCase = true)) {
            "https://img.youtube.com/vi/$key/0.jpg"
        } else {
            ""
        }
    }

    fun getYoutubeUrl(): String {
        return if (site.equals("Youtube", ignoreCase = true)) {
            "http://www.youtube.com/watch?v=%1$key"
        } else {
            ""
        }
    }
}
