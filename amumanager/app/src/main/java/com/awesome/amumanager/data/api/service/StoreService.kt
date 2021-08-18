package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.StoreResponse
import com.awesome.amumanager.data.model.Store
import retrofit2.Call
import retrofit2.http.*

interface StoreService {
    @Headers("accept: application/json",
        "content-type: application/json")

    @PUT("/openStore")
    fun openStore(@Query("storeId") storeId : String) : Call<DefaultResponse>

    @PUT("/closeStore")
    fun closeStore(@Query("storeId") storeId : String) : Call<DefaultResponse>

    @DELETE("/deleteStore")
    fun deleteStore(@Query("storeId") storeId : String) : Call<DefaultResponse>

    @POST("store")
    fun addStore(@Body params: Store) : Call<DefaultResponse>

    @GET("/getStore")
    fun getStore(@Query("uid") uid:String, @Query("lastId") lastId: String) : Call<StoreResponse>
}