package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ReserveResponse
import retrofit2.Call
import retrofit2.http.*

interface GetConfirmedReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getConfirmedReserveList")
    fun getConfirmedReserveList(@Query("store_id") uid:String, @Query("lastId") lastId: String) : Call<ReserveResponse>
}