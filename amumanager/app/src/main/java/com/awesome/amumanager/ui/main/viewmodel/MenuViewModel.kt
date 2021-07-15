package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.data.model.remote.MenuApi

class MenuViewModel(private val storeId: String?) : ViewModel() {
    private val menuApi = MenuApi()
    val menus = MutableLiveData<ArrayList<Menu>>()
    val status = MutableLiveData<Int>()


    fun getMenuList() {
        menuApi.getMenuList(menus, storeId!!)
    }

    fun addMenu(menu : Menu) {
        menuApi.addMenu(menu, status)
    }
}