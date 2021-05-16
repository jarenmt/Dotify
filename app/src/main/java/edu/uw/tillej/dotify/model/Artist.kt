package edu.uw.tillej.dotify.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val name: String,
    val smallImageURL: String,
    val largeImageURL: String
): Parcelable