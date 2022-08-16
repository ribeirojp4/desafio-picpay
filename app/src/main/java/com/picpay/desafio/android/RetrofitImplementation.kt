package com.picpay.desafio.android

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Abstraction of a Retrofit implementation.
 *
 * Creating an instance of this class implies into a Retrofit instance Build with a related
 * HttpClient to catch and log http calls.
 *
 * In order to get some service, clients must call:
 *
 * {{RetrofitImplementation(<SomeService>::class.java).getService()}}
 */
class RetrofitImplementation<T>(private val serviceClass: Class<T>) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .readTimeout(READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .callTimeout(CALL_TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .build()

    fun getService(): T = retrofit.create(serviceClass)

    companion object {
        private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
        private const val READ_TIME_OUT_SECONDS = 30L
        private const val CALL_TIME_OUT_SECONDS = 30L
    }
}
