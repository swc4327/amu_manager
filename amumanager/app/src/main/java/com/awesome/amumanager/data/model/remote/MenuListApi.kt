package com.awesome.amumanager.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.MenuListResponse
import com.awesome.amumanager.data.model.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuListApi {

    fun getMenuList(
            menuList: MutableLiveData<ArrayList<Menu>>,
            storeId: String) {

        var menusTemp = ArrayList<Menu>()

        val joinApi = RetrofitObject.getMenuListService

        joinApi.getMenuList(storeId.toString())
                .enqueue(object : Callback<MenuListResponse> {

                    override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
                        Log.e("Menu Retrofit getMenu", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<MenuListResponse>,
                            response: Response<MenuListResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("Getmenu Retrofit", "success")
                            menusTemp.addAll(response.body()!!.menus)
                            menuList.value = menusTemp
                        } else {

                        }
                    }
                })
    }

    fun addMenu(menu: Menu, status: MutableLiveData<Int>) {
        val joinApi = RetrofitObject.addMenuService

        joinApi.addMenu(menu)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("Retrofit Add Menu", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("AddMenuActivity", "success")
                            status.value = 200
                        } else {
                            Log.e("AddMenuActivity", "실패")
                        }
                    }
                })
    }
}