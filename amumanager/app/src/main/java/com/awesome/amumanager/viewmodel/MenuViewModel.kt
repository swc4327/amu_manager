package com.awesome.amumanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.model.Menu
import com.awesome.amumanager.model.retrofit.RetrofitFactory

class MenuViewModel(private val storeId: String?) : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    val menuList = MutableLiveData<ArrayList<Menu>>()
    private val menus = ArrayList<Menu>()

    init {
        menuList.value = menus
        getMenuList()
    }

    fun getMenuList() {
        retrofitFactory.getMenuList(menuList, menus, storeId!!)
    }
}