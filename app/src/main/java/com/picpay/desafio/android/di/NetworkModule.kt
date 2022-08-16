package com.picpay.desafio.android.di

import com.picpay.desafio.android.RetrofitImplementation
import com.picpay.desafio.android.usersFeature.api.UserService
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkModule : ComponentModule {
    override fun provides(): Module = module {
        single { RetrofitImplementation(UserService::class.java).getService() }
    }
}
