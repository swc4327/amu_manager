package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.model.Store
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddStoreService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("store")
    fun addStore(@Body params: Store) : Call<DefaultResponse>
}