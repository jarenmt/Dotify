package edu.uw.tillej.dotify.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Int,
    val profilePictureURL: String,
): Parcelable
