package com.awesome.amumanager.domain.usecase.store

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.store.AmuManagerStoreRepository
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class GetStoreUseCase @Inject constructor(private val repository: AmuManagerStoreRepository) {
    operator fun invoke(stores: MutableLiveData<ArrayList<Store>>, uid: String, lastId: String) {
        repository.getStore(stores, uid, lastId)
    }
}