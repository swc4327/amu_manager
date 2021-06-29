package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.ReserveListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReserveListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReserveList")
    fun getReserveList(@Query("store_id") uid:String) : Call<ReserveListResponse>
}