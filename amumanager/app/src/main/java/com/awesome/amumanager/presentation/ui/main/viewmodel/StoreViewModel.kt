package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.Store
import com.awesome.amumanager.domain.usecase.store.*
import com.awesome.amumanager.domain.usecase.test.GetStoreTestUseCase
import javax.inject.Inject

class StoreViewModel @Inject constructor(
    private val getStoreUseCase: GetStoreUseCase,
    private val addStoreUseCase: AddStoreUseCase,
    private val deleteStoreUseCase: DeleteStoreUseCase,
    private val openStoreUseCase: OpenStoreUseCase,
    private val closeStoreUseCase: CloseStoreUseCase,

    private val getStoreTestUseCase: GetStoreTestUseCase
) : ViewModel() {
    val stores = MutableLiveData<ArrayList<Store>>()

    val status = MutableLiveData<Int>()

    fun getStore(uid: String, lastId : String) {
        getStoreUseCase(stores, uid, lastId)
    }

    fun getStoreTest(uid: String, lastId : String) {
        getStoreTestUseCase(stores, uid, lastId)
    }

    fun addStore(store : Store) {
        addStoreUseCase(store, status)
    }

    fun deleteStore(storeId : String) {
        deleteStoreUseCase(storeId, status)
    }

    fun openStore(storeId : String) {
        openStoreUseCase(storeId, status)
    }

    fun closeStore(storeId : String) {
        closeStoreUseCase(storeId, status)
    }
}