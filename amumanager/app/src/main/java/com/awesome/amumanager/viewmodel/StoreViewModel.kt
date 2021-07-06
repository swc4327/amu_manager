package com.awesome.amumanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.model.retrofit.RetrofitFactory
import com.awesome.amumanager.model.Store

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofitFactory = RetrofitFactory()
    val storeList = MutableLiveData<ArrayList<Store>>()
    private val stores = ArrayList<Store>()

    init {
        storeList.value = stores
        getStoreList()
    }

    fun getStoreList() {
        retrofitFactory.getStoreList(storeList, stores)
    }
}