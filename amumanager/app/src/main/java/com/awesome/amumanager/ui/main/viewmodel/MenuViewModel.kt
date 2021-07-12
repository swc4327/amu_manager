package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.data.model.remote.MenuApi

class MenuViewModel(private val storeId: String?) : ViewModel() {
    private val menuListFactory = MenuApi()
    val menuList = MutableLiveData<ArrayList<Menu>>()
    val status = MutableLiveData<Int>()


    fun getMenuList() {
        menuListFactory.getMenuList(menuList, storeId!!)
    }

    fun addMenu(menu : Menu) {
        menuListFactory.addMenu(menu, status)
    }
}