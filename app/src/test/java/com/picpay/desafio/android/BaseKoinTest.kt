package com.picpay.desafio.android

import io.mockk.mockkClass
import org.junit.Rule
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule

abstract class BaseKoinTest : KoinTest{

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(initModules()) }

    @get:Rule
    val mockProvider = MockProviderRule.create { kClass ->
        mockkClass(kClass, relaxed = true)
    }

    abstract fun initModules(): List<Module>
}
