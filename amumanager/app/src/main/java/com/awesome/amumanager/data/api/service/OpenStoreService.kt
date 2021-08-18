package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface OpenStoreService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @PUT("/openStore")
    fun openStore(@Query("storeId") storeId : String) : Call<DefaultResponse>
}