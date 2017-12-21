package com.rudearts.reposearcher.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestController {

    companion object {
        val END_POINT = "https://api.github.com"
        const val USERNAME_PATH = "username"
    }

    /**
     * It is lateinit only because compilator is not allowing it to be other way...
     */
    internal lateinit var restApi: RestAPI

    init {
        setup()
    }

    internal fun setup() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        restApi = retrofit.create(RestAPI::class.java)
    }

    fun provideRestApi() = restApi
}
