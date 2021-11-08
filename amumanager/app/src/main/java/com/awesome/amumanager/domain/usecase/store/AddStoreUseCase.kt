package com.awesome.amumanager.domain.usecase.store

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.store.AmuManagerStoreRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class AddStoreUseCase @Inject constructor(private val repository: AmuManagerStoreRepository) {
    operator fun invoke(store : Store, status : MutableLiveData<Int>) {
        repository.addStore(store, status)
    }
}