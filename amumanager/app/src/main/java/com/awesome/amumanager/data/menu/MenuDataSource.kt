package com.awesome.amumanager.data.menu

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.MenuResponse
import com.awesome.amumanager.data.api.service.MenuApi
import com.awesome.amumanager.domain.model.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface MenuDataSource {
    fun getMenu(
        menus: MutableLiveData<ArrayList<Menu>>,
        storeId: String,
        lastId: String
    )
    fun addMenu(menu: Menu, status: MutableLiveData<Int>)
    fun deleteMenu(menuId: String, status : MutableLiveData<Int>)
}

class AmuManagerMenuDataSource @Inject constructor(
    private val menuApi: MenuApi
) : MenuDataSource {
    override fun getMenu(menus: MutableLiveData<ArrayList<Menu>>, storeId: String, lastId: String) {
        menuApi.getMenuList(storeId.toString(), lastId)
            .enqueue(object : Callback<MenuResponse> {

                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    Log.e("Menu Retrofit getMenu", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Get menu Retrofit", "success")
                        menus.value = response.body()!!.menus
                    } else {

                    }
                }
            })
    }

    override fun addMenu(menu: Menu, status: MutableLiveData<Int>) {
        menuApi.addMenu(menu)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit Add Menu", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("AddMenuActivity", "success")
                        status.value = 200
                    } else {
                        Log.e("AddMenuActivity", "실패")
                    }
                }
            })
    }

    override fun deleteMenu(menuId: String, status: MutableLiveData<Int>) {
        menuApi.deleteMenu(menuId)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit delete Menu", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("delete Menu", "success")
                        status.value = 200
                    } else {
                        Log.e("delete menu", "실패")
                    }
                }
            })
    }

}