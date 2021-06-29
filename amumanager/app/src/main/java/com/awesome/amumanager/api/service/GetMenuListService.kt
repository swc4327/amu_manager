package com.awesome.amumanager.api.service

import com.awesome.amumanager.api.response.MenuListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetMenuListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getMenuList")
    fun getMenuList(@Query("store_id") store_id:String) : Call<MenuListResponse>
}