package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.PromotionResponse
import com.awesome.amumanager.domain.model.Promotion
import retrofit2.Call
import retrofit2.http.*

interface PromotionApi {
    @Headers("accept: application/json",
        "content-type: application/json")

    @GET("/getPromotionList")
    fun getPromotionList(@Query("storeId") storeId:String, @Query("lastId") lastId: String) : Call<PromotionResponse>

    @POST("promotion")
    fun addPromotion(@Body params: Promotion) : Call<DefaultResponse>

    @DELETE("/endPromotion")
    fun endPromotion(@Query("promotionId") promotionId : String) : Call<DefaultResponse>
}