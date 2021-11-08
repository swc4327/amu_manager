package com.awesome.amumanager.domain.usecase.store

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.store.AmuManagerStoreRepository
import javax.inject.Inject

class OpenStoreUseCase @Inject constructor(private val repository: AmuManagerStoreRepository) {
    operator fun invoke(storeId : String, status : MutableLiveData<Int>) {
        repository.openStore(storeId, status)
    }
}