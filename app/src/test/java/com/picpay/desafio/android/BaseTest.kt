package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest : TestWatcher(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        UnconfinedTestDispatcher()
    }

    val testDispatcher by lazy { UnconfinedTestDispatcher() }

    fun <T> LiveData<T>.observeForTesting(block: (observer: Observer<T>) -> Unit) {
        val observer = mockk<Observer<T>>(relaxed = true)
        try {
            observeForever(observer)
            block(observer)
        } finally {
            removeObserver(observer)
        }
    }
}
