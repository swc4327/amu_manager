package com.awesome.amumanager.domain.usecase.store

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.store.AmuManagerStoreRepository
import javax.inject.Inject

class CloseStoreUseCase @Inject constructor(private val repository: AmuManagerStoreRepository) {
    operator fun invoke(storeId : String, status : MutableLiveData<Int>) {
        repository.closeStore(storeId, status)
    }
}