package com.picpay.desafio.android

sealed class ViewState<out T> {
    data class ViewErrorState<out T>(val errorData: Error?) : ViewState<T>()
    data class ViewSuccessState<out T>(val data: T) : ViewState<T>()
    object ViewLoadingState : ViewState<Nothing>()
}
