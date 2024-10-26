package com.fanz.oways.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val cardType: String? = null,
    val club_code: String? = null,
    val name: String? = null,
    val nationality: String? = null,
    val number: Long? = null,
    val playerPicture: String? = null,
    val position: String? = null,
    val price: String? = null
    ):Parcelable
