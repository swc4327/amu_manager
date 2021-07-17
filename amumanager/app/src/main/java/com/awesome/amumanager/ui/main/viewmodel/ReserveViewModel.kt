package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.remote.ReserveApi

class ReserveViewModel(private var storeId: String) : ViewModel() {
    private val reserveApi = ReserveApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList() {
        reserveApi.getReserveList(reserveLists, storeId)
    }

    fun confirmReserve(reserveId: String) {
        reserveApi.confirmReserve(reserveId, status)
    }
    //거부
    fun cancelReserve(reserveId : String) {
        reserveApi.cancelReserve(reserveId, status)
    }

}