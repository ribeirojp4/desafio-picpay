package com.picpay.desafio.android.usersFeature.api

import com.picpay.desafio.android.usersFeature.data.UserPayload
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<UserPayload>
}
