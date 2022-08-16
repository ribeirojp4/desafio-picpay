package com.picpay.desafio.android.usersFeature.data

import com.google.gson.annotations.SerializedName

class UserPayload(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
)
