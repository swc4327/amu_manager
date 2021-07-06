package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ReserveListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReserveListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReserveList")
    fun getReserveList(@Query("store_id") uid:String) : Call<ReserveListResponse>
}