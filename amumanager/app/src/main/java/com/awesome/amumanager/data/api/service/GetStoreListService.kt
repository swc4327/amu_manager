package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.StoreListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetStoreListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getStoreList")
    fun getStoreList(@Query("uid") uid:String) : Call<StoreListResponse>
}