package com.picpay.desafio.android.di

import com.picpay.desafio.android.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class DatabaseModule : ComponentModule {
    override fun provides(): Module = module {
        single { AppDatabase.getInstance(androidApplication()) }
        single {get<AppDatabase>().userDao()}
    }
}
