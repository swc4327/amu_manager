package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ReviewListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReviewListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReviewList")
    fun getReviewList(@Query("store_id") store_id:String) : Call<ReviewListResponse>
}