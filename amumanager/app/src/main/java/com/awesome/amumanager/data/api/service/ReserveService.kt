package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.ReserveResponse
import retrofit2.Call
import retrofit2.http.*

interface ReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")

    @GET("/getReserveList")
    fun getReserveList(@Query("storeId") storeId : String, @Query("lastId") lastId: String) : Call<ReserveResponse>

    @GET("/getConfirmedReserveList")
    fun getConfirmedReserveList(@Query("storeId") storeId:String, @Query("lastId") lastId: String) : Call<ReserveResponse>

    @PUT("/confirmReserve")
    fun confirmReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>

    @PUT("/completeReserve")
    fun completeReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>

    @DELETE("/cancelReserve")
    fun cancelReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>

}