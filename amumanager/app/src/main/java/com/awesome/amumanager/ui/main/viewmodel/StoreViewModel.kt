package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.data.model.remote.StoreApi

class StoreViewModel() : ViewModel() {
    private val storeApi = StoreApi()
    val stores = MutableLiveData<ArrayList<Store>>()
    val status = MutableLiveData<Int>()

    fun getStore(uid: String) {
        storeApi.getStore(stores, uid)
    }

    fun addStore(store : Store) {
        storeApi.addStore(store, status)
    }
}