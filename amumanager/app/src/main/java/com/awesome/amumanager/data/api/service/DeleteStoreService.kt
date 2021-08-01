package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface DeleteStoreService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("/deleteStore")
    fun deleteStore(@Query("storeId") storeId : String) : Call<DefaultResponse>
}