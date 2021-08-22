package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.MenuResponse
import com.awesome.amumanager.data.model.Menu
import retrofit2.Call
import retrofit2.http.*

interface MenuService {
    @Headers("accept: application/json",
        "content-type: application/json")

    @GET("/getMenuList")
    fun getMenuList(@Query("storeId") storeId:String, @Query("lastId") lastId:String) : Call<MenuResponse>

    @POST("menu")
    fun addMenu(@Body params: Menu) : Call<DefaultResponse>

    @DELETE("/deleteMenu")
    fun deleteMenu(@Query("menuId") menuId : String) : Call<DefaultResponse>

}