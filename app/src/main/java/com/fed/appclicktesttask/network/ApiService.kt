package com.fed.appclicktesttask.network

import com.fed.appclicktesttask.model.POJO
import retrofit2.Call

import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/adviator/index.php")
    fun requestForAd(@Query("id") id: Long): Call<POJO>
}
