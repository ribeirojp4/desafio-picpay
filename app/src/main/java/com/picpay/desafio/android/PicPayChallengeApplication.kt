package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.DatabaseModule
import com.picpay.desafio.android.di.NetworkModule
import com.picpay.desafio.android.di.UsersFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module


class PicPayChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        injectModulesWithKoin(
            NetworkModule().provides(),
            DatabaseModule().provides(),
            UsersFeatureModule().provides()
        )
    }

    private fun Application.injectModulesWithKoin(vararg modules: Module) {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(listOf(*modules))
        }
    }
}
