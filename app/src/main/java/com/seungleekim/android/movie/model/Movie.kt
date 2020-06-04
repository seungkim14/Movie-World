package com.seungleekim.android.movie.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Movie(
    @PrimaryKey @NonNull @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "last_name") @SerializedName("poster_path")
    val posterPath: String,
    @ColumnInfo(name = "release_date") @SerializedName("release_date")
    val releaseDate: String
) : Parcelable

fun Movie.getPosterUrl(): String {
    return "http://image.tmdb.org/t/p/w342$posterPath"
}
