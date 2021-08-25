package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.remote.ReserveApi
import javax.inject.Inject

class ReserveViewModel @Inject constructor() : ViewModel() {
    private val reserveApi = ReserveApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList(lastId : String, storeId : String) {
        reserveApi.getReserveList(reserveLists, storeId, lastId)
    }

    fun confirmReserve(reserveId: String) {
        reserveApi.confirmReserve(reserveId, status)
    }

    fun getConfirmedReserveList(lastId : String, storeId : String) {
        reserveApi.getConfirmedReserveList(reserveLists, storeId, lastId)
    }
    //거부
    fun cancelReserve(reserveId : String) {
        reserveApi.cancelReserve(reserveId, status)
    }

    fun completeReserve(reserveId : String) {
        reserveApi.completeReserve(reserveId, status)
    }

}