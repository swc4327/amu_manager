package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.data.model.remote.MenuApi
import javax.inject.Inject

class MenuViewModel @Inject constructor() : ViewModel() {
    private val menuApi = MenuApi()
    val menus = MutableLiveData<ArrayList<Menu>>()
    val status = MutableLiveData<Int>()


    fun getMenu(lastId : String, storeId : String) {
        menuApi.getMenu(menus, storeId, lastId)
    }

    fun addMenu(menu : Menu) {
        menuApi.addMenu(menu, status)
    }

    fun deleteMenu(menuId: String) {
        menuApi.deleteMenu(menuId, status)
    }
}