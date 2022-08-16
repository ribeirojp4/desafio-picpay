package com.picpay.desafio.android.usersFeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.ResultWrapper
import com.picpay.desafio.android.ViewState
import com.picpay.desafio.android.usersFeature.data.UserVO
import kotlinx.coroutines.CoroutineDispatcher

class UsersViewModel(
    private val usersRepository: UsersRepository,
    dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext + dispatcher

    fun users(forceFetch: Boolean = false): LiveData<ViewState<List<UserVO>>> =
        liveData(coroutineContext) {
            emit(ViewState.ViewLoadingState)
            when (val usersResponse = usersRepository.getUsers(forceFetch = forceFetch)) {
                is ResultWrapper.Success -> emit(ViewState.ViewSuccessState(usersResponse.data))
                is ResultWrapper.NetworkError -> emit(ViewState.ViewErrorState(Error()))
                is ResultWrapper.GenericError -> emit(
                    ViewState.ViewErrorState(
                        errorData = Error(
                            usersResponse.code?.toString(),
                            usersResponse.error
                        )
                    )
                )
            }
        }
}
