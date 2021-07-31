package com.awesome.amumanager.data.api.service

import com.awesome.amumanager.data.api.response.DefaultResponse
import retrofit2.Call
import retrofit2.http.*

interface DeleteMenuService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("/deleteMenu")
    fun deleteMenu(@Query("menuId") menuId : String) : Call<DefaultResponse>
}