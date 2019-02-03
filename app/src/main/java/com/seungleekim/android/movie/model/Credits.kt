package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(
    val casts: List<Cast>,
    val crews: List<Crew>
) : Parcelable