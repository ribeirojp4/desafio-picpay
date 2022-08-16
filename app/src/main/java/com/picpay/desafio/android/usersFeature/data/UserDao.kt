package com.picpay.desafio.android.usersFeature.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(users: List<User>)

    @Query("DELETE FROM user")
    fun deleteAll()
}
