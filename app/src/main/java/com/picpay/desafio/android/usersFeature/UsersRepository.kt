package com.picpay.desafio.android.usersFeature

import com.picpay.desafio.android.ResultWrapper
import com.picpay.desafio.android.usersFeature.data.UserDao
import com.picpay.desafio.android.usersFeature.data.UserVO
import com.picpay.desafio.android.usersFeature.data.toUsers
import com.picpay.desafio.android.usersFeature.data.toUsersVO
import com.picpay.desafio.android.safeApiCall
import com.picpay.desafio.android.usersFeature.api.UserService
import kotlinx.coroutines.CoroutineDispatcher

class UsersRepository(
    private val userService: UserService,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getUsers(forceFetch: Boolean): ResultWrapper<List<UserVO>> {
        return if (forceFetch) {
            getUsersRemoteAndUpdateLocal()
        } else {
            getUsersLocal()
        }
    }

    private suspend fun getUsersLocal() = try {
        userDao.getAll().let {
            if (it.isEmpty()) getUsersRemoteAndUpdateLocal()
            else ResultWrapper.Success(userDao.getAll().toUsersVO())
        }
    } catch (e: Exception) {
        ResultWrapper.GenericError()
    }

    /**
     * Fetch data from remote data source and updates local storage.
     *
     * @return fetched data from remote storage
     */
    private suspend fun getUsersRemoteAndUpdateLocal() = safeApiCall(dispatcher) {
        val usersPayload = userService.getUsers()
        userDao.apply {
            deleteAll()
            insertAll(usersPayload.toUsers())
        }
        usersPayload.toUsersVO()
    }
}
