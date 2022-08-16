package com.picpay.desafio.android

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.usersFeature.UsersRepository
import com.picpay.desafio.android.usersFeature.UsersViewModel
import com.picpay.desafio.android.usersFeature.api.UserService
import com.picpay.desafio.android.usersFeature.data.UserDao
import com.picpay.desafio.android.usersFeature.data.UserVO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest : BaseTest() {

    private val mockUserService: UserService = mockk(relaxed = true)
    private val mockUserDao: UserDao = mockk(relaxed = true)
    private val mockUsersRepository: UsersRepository =
        UsersRepository(mockUserService, mockUserDao, testDispatcher)

    private val mockViewModel = UsersViewModel(
        usersRepository = mockUsersRepository,
        dispatcher = UnconfinedTestDispatcher()
    )

    @Test
    fun `WHEN get users from remote is success THEN live data must return success view state`() =
        runTest(testDispatcher) {
            val expected = ViewState.ViewSuccessState(mockUserVOList)

            coEvery { mockUserService.getUsers() } returns mockUserPayloadList

            val usersLiveData: LiveData<ViewState<List<UserVO>>> = mockViewModel.users(true)

            usersLiveData.observeForTesting {
                assertEquals(usersLiveData.value, expected)
            }
        }

    @Test
    fun `WHEN get users from remote is error THEN live data must return error view state`() =
        runTest(testDispatcher) {
            coEvery { mockUserService.getUsers() } throws Error()

            val usersLiveData = mockViewModel.users(true) as LiveData<*>

            usersLiveData.observeForTesting {
                assert(usersLiveData.value is ViewState.ViewErrorState<*>)
            }
        }
}
