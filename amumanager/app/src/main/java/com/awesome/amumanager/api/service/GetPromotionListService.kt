package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.PromotionListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface GetPromotionListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getPromotionList")
    fun getPromotionList(@Query("store_id") store_id:String) : Call<PromotionListResponse>
}