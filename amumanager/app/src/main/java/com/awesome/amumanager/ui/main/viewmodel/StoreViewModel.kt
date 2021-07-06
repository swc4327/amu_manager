package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.retrofit.RetrofitFactory
import com.awesome.amumanager.data.model.Store

class StoreViewModel() : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    val storeList = MutableLiveData<ArrayList<Store>>()

    fun getStoreList() {
        retrofitFactory.getStoreList(storeList)
    }
}