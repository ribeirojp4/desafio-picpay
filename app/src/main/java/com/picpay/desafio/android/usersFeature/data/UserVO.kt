package com.picpay.desafio.android.usersFeature.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserVO(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable
