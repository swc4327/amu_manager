package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.ReviewResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReviewService {
    @Headers("accept: application/json",
        "content-type: application/json")

    @GET("/getReviewList")
    fun getReviewList(@Query("storeId") storeId:String, @Query("lastId") lastId: String) : Call<ReviewResponse>

    @DELETE("reviewFiltering")
    fun filterReview(@Query("reviewId") reviewId: String?, @Query("storeId") storeId: String?) : Call<DefaultResponse>
}