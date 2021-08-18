package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.PromotionResponse
import com.awesome.amumanager.data.model.Promotion
import retrofit2.Call
import retrofit2.http.*

interface PromotionService {
    @Headers("accept: application/json",
        "content-type: application/json")

    @GET("/getPromotionList")
    fun getPromotionList(@Query("store_id") store_id:String, @Query("lastId") lastId: String) : Call<PromotionResponse>

    @POST("promotion")
    fun addPromotion(@Body params: Promotion) : Call<DefaultResponse>

    @DELETE("/endPromotion")
    fun endPromotion(@Query("promotionId") promotionId : String) : Call<DefaultResponse>
}