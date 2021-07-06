package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.data.model.retrofit.RetrofitFactory

class MenuViewModel(private val storeId: String?) : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    val menuList = MutableLiveData<ArrayList<Menu>>()


    fun getMenuList() {
        retrofitFactory.getMenuList(menuList, storeId!!)
    }
}