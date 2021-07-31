package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface EndPromotionService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("/endPromotion")
    fun endPromotion(@Query("promotionId") promotionId : String) : Call<DefaultResponse>
}