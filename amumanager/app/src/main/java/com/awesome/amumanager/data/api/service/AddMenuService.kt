package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.model.Menu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddMenuService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("menu")
    fun addMenu(@Body params: Menu) : Call<DefaultResponse>
}