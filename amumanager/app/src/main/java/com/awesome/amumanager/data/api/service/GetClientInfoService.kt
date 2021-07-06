package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ClientResponse
import retrofit2.Call
import retrofit2.http.*

interface GetClientInfoService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getClient")
    fun getClient(@Query("client_id") client_id:String) : Call<ClientResponse>
}