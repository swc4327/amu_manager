package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.remote.ReserveApi

class ReserveViewModel(private var storeId: String) : ViewModel() {
    private val reserveApi = ReserveApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    private var reservesTemp = ArrayList<Reserve>()
    val status = MutableLiveData<Int>()

    fun getReserveList(lastId : String) {
        reserveApi.getReserveList(reserveLists, storeId, lastId, reservesTemp)
    }

    fun confirmReserve(reserveId: String) {
        reserveApi.confirmReserve(reserveId, status)
    }
    //거부
    fun cancelReserve(reserveId : String) {
        reserveApi.cancelReserve(reserveId, status)
    }

    fun completeReserve(reserveId : String) {
        reserveApi.completeReserve(reserveId, status)
    }

}