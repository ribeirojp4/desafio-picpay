package com.picpay.desafio.android

import com.picpay.desafio.android.di.UsersFeatureModule
import com.picpay.desafio.android.usersFeature.UsersViewModel
import org.junit.Test
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.test.get
import kotlin.test.assertTrue

class UserModuleTest : BaseKoinTest() {
    override fun initModules(): List<Module> = listOf(UsersFeatureModule().provides())

    @Test
    fun foo() {
        val usersViewmodel: UsersViewModel = get() {
            parametersOf()
        }
        assertTrue(usersViewmodel is UsersViewModel)
    }

}
