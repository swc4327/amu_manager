package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.model.Manager
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("manager")
    fun addManager(@Body params: Manager) : Call<DefaultResponse>
}