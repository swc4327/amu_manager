package com.awesome.amumanager.data.reserve

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.ReserveList
import javax.inject.Inject

interface ReserveRepository {
    fun getReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    )
    fun getConfirmedReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    )
    fun confirmReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    )
    fun cancelReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    )
    fun completeReserve(
        reserveId : String,
        status : MutableLiveData<Int>
    )
}

class AmuManagerReserveRepository @Inject constructor(
    private val reserveDataSource: ReserveDataSource
) : ReserveRepository {
    override fun getReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    ) {
        reserveDataSource.getReserveList(ReserveLists, storeId, lastId)
    }

    override fun getConfirmedReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    ) {
        reserveDataSource.getConfirmedReserveList(ReserveLists, storeId, lastId)
    }

    override fun confirmReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    ) {
        reserveDataSource.confirmReserve(reserveId, status)
    }

    override fun cancelReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    ) {
        reserveDataSource.cancelReserve(reserveId, status)
    }
    override fun completeReserve(
        reserveId : String,
        status : MutableLiveData<Int>
    ) {
        reserveDataSource.completeReserve(reserveId, status)
    }
}