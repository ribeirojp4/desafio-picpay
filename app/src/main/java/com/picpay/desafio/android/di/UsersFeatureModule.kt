package com.picpay.desafio.android.di

import com.picpay.desafio.android.usersFeature.UsersRepository
import com.picpay.desafio.android.usersFeature.UsersViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class UsersFeatureModule : ComponentModule {
    override fun provides(): Module = module {
        single { Dispatchers.IO }
        single { UsersRepository(get(), get(), get())}
        viewModel { UsersViewModel(get(), get()) }
    }
}
