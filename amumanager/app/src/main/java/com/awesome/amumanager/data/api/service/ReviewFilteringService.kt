package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface ReviewFilteringService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("reviewFiltering")
    fun filterReview(@Query("review_id") review_id: String?, @Query("store_id") store_id: String?) : Call<DefaultResponse>
}