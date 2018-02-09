package com.fed.appclicktesttask.network

import com.fed.appclicktesttask.model.responsePOJO
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //
    @Headers(
        "Cache-Control: no-cache",
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("/adviator/index.php")
    fun requestForAd(@Query("id") id: Long): Call<responsePOJO>
}
