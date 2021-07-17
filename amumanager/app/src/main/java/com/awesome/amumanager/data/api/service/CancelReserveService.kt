package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface CancelReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("/cancelReserve")
    fun cancelReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>
}