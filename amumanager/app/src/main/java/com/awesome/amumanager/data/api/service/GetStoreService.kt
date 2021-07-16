package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.StoreResponse
import retrofit2.Call
import retrofit2.http.*

interface GetStoreService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getStore")
    fun getStore(@Query("uid") uid:String, @Query("itemCount") itemCount: String) : Call<StoreResponse>
}