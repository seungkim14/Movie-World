package com.seungleekim.android.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
//    @SerializedName("poster_path")
    val posterPath: String,
//    @SerializedName("vote_average")
    val rating: Double
) : Parcelable
