package com.seungleekim.android.movie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cast (
    val id: Int,
    val order: Int,
    val name: String,
    val character: String
) : Parcelable {

    override fun toString() = "$name ($character)"

}
