package com.fanz.oways.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    @SerializedName("code")
    val code:String? = null,
    @SerializedName("id")
    val id:String? = null,
    @SerializedName("image")
    val image:String? = null,
    @SerializedName("name")
    val name:String? = null
):Parcelable
