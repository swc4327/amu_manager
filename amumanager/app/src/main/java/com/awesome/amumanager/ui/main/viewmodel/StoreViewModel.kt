package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.data.model.factory.StoreListFactory

class StoreViewModel() : ViewModel() {
    private val storeListFactory = StoreListFactory()
    val storeList = MutableLiveData<ArrayList<Store>>()
    val status = MutableLiveData<Int>()

    fun getStoreList(uid: String) {
        storeListFactory.getStoreList(storeList, uid)
    }

    fun addStore(store : Store) {
        storeListFactory.addStore(store, status)
    }
}