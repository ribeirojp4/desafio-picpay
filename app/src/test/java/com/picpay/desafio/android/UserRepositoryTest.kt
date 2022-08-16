package com.picpay.desafio.android

import com.picpay.desafio.android.usersFeature.UsersRepository
import com.picpay.desafio.android.usersFeature.api.UserService
import com.picpay.desafio.android.usersFeature.data.UserDao
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest : BaseTest() {

    private val mockUserService = mockk<UserService>(relaxed = true)
    private val mockUserDao: UserDao = mockk(relaxed = true)
    private val mockUsersRepository: UsersRepository =
        UsersRepository(mockUserService, mockUserDao, testDispatcher)

    @Test
    fun `WHEN get users with force fetch THEN return users from remote storage`() =
        runTest(testDispatcher) {
            coEvery { mockUserService.getUsers() } returns mockUserPayloadList

            val usersVO = mockUsersRepository.getUsers(forceFetch = true)

            assertEquals(usersVO, ResultWrapper.Success(mockUserVOList))
        }

    @Test
    fun `When get users without force fetch THEN return users from local storage`() {
        runTest(testDispatcher) {
            every { mockUserDao.getAll() } returns mockUsersList

            val usersVO = mockUsersRepository.getUsers(forceFetch = false)

            assertEquals(usersVO, ResultWrapper.Success(mockUserVOList))
        }
    }

    @Test
    fun `WHEN get users without force fetch and has no cached data THEN return users from remote storage`() =
        runTest(testDispatcher) {
            every { mockUserDao.getAll() } returns emptyList()
            coEvery { mockUserService.getUsers() } returns mockUserPayloadList

            val usersVO = mockUsersRepository.getUsers(forceFetch = false)

            assertEquals(usersVO, ResultWrapper.Success(mockUserVOList))
        }
}
