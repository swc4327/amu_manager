package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.ClientResponse
import retrofit2.Call
import retrofit2.http.*

interface GetClientService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getClient")
    fun getClient(@Query("clientId") clientId:String) : Call<ClientResponse>
}