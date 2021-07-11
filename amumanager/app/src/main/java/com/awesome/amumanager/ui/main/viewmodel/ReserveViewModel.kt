package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.remote.ReserveListApi

class ReserveViewModel(private var storeId: String) : ViewModel() {
    private val reserveListFactory = ReserveListApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList() {
        reserveListFactory.getReserveList(reserveLists, storeId)
    }

}