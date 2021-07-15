package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.remote.ReserveApi

class ReserveViewModel(private var storeId: String) : ViewModel() {
    private val reserveListApi = ReserveApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList() {
        reserveListApi.getReserveList(reserveLists, storeId)
    }

}