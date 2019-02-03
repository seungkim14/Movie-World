package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review (
    val id: String,
    val author: String,
    val content: String
) : Parcelable