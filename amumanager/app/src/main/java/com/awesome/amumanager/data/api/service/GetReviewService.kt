package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ReviewResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReviewService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReviewList")
    fun getReviewList(@Query("store_id") store_id:String, @Query("itemCount") itemCount: String) : Call<ReviewResponse>
}