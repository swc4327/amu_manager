package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.data.model.factory.ReserveListFactory

class ReserveViewModel(private var storeId: String) : ViewModel() {
    private val reserveListFactory = ReserveListFactory()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList() {
        reserveListFactory.getReserveList(reserveLists, storeId)
    }

}