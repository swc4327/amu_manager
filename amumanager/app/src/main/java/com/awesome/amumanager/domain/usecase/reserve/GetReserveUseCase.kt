package com.awesome.amumanager.domain.usecase.reserve

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.reserve.ReserveRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.model.ReserveList
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class GetReserveUseCase @Inject constructor(private val repository: ReserveRepository) {
    operator fun invoke(ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
                        storeId: String,
                        lastId: String) {
        repository.getReserveList(ReserveLists, storeId, lastId)
    }
}