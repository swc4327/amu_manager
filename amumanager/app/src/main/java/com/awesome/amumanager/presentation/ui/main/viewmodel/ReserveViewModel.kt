package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.ReserveList
import com.awesome.amumanager.domain.usecase.reserve.CompleteReserveUseCase
import com.awesome.amumanager.domain.usecase.reserve.ConfirmReserveUseCase
import com.awesome.amumanager.domain.usecase.reserve.GetConfirmedReserveUseCase
import com.awesome.amumanager.domain.usecase.reserve.GetReserveUseCase
import javax.inject.Inject

class ReserveViewModel @Inject constructor(
    private val getReserveUseCase: GetReserveUseCase,
    private val confirmReserveUseCase: ConfirmReserveUseCase,
    private val getConfirmedReserveUseCase: GetConfirmedReserveUseCase,
    private val cancelReserveUseCase: ConfirmReserveUseCase,
    private val completeReserveUseCase: CompleteReserveUseCase
) : ViewModel() {

    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    val status = MutableLiveData<Int>()

    fun getReserveList(lastId : String, storeId : String) {
        getReserveUseCase(reserveLists, storeId, lastId)
    }

    fun confirmReserve(reserveId: String) {
        confirmReserveUseCase(reserveId, status)
    }

    fun getConfirmedReserveList(lastId : String, storeId : String) {
        getConfirmedReserveUseCase(reserveLists, storeId, lastId)
    }
    //거부
    fun cancelReserve(reserveId : String) {
        cancelReserveUseCase(reserveId, status)
    }

    fun completeReserve(reserveId : String) {
        completeReserveUseCase(reserveId, status)
    }

}