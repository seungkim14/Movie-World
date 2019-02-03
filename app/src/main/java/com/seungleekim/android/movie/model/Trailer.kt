package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Trailer(
    val id: String,
    val site: String,
    val key: String
) : Parcelable
