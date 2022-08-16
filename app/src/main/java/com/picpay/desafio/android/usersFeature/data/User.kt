package com.picpay.desafio.android.usersFeature.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "img_url") val img: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_name") val username: String
)
