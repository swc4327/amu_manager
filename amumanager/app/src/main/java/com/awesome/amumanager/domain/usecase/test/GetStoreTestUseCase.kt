package com.awesome.amumanager.domain.usecase.test

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.store.AmuManagerStoreTestRepository
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class GetStoreTestUseCase @Inject constructor(private val repository: AmuManagerStoreTestRepository) {
    operator fun invoke(stores: MutableLiveData<ArrayList<Store>>, uid: String, lastId: String) {
        repository.getStore(stores, uid, lastId)
    }
}