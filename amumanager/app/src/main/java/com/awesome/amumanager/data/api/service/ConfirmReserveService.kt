package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface ConfirmReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @PUT("/confirmReserve")
    fun confirmReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>
}