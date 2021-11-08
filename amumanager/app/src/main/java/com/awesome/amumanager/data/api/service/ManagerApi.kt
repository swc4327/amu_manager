package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.domain.model.Manager
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ManagerApi {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("manager")
    fun addManager(@Body params: Manager) : Call<DefaultResponse>
}