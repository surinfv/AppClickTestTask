package com.fed.appclicktesttask.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private val BASE_URL = "http://www.505.rs "

    private val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    val apiService: ApiService
        get() = retrofitInstance.create(ApiService::class.java)


}
