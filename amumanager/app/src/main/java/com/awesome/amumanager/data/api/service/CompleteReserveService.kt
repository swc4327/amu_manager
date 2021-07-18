package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface CompleteReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @PUT("/completeReserve")
    fun completeReserve(@Query("reserveId") reserveId : String) : Call<DefaultResponse>
}