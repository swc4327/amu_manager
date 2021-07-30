package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.PromotionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface GetPromotionService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getPromotionList")
    fun getPromotionList(@Query("store_id") store_id:String, @Query("lastId") lastId: String) : Call<PromotionResponse>
}