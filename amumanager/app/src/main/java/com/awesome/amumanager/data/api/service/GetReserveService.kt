package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ReserveResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReserveList")
    fun getReserveList(@Query("store_id") uid:String) : Call<ReserveResponse>
}