package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.StoreListResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GetStoreListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getStoreList")
    fun getStoreList(@Query("uid") uid:String) : Call<StoreListResponse>
}