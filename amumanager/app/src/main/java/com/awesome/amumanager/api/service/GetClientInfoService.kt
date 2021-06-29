package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.ClientResponse
import retrofit2.Call
import retrofit2.http.*

interface GetClientInfoService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getClient")
    fun getClient(@Query("client_id") client_id:String) : Call<ClientResponse>
}