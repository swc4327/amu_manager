package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.MenuResponse
import retrofit2.Call
import retrofit2.http.*

interface GetMenuService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getMenuList")
    fun getMenuList(@Query("store_id") store_id:String, @Query("lastId") lastId:String) : Call<MenuResponse>
}