package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val id: Int,
    val name: String,
    val department: String,
    val job: String
) : Parcelable {

    override fun toString() = "$name ($job)"
}