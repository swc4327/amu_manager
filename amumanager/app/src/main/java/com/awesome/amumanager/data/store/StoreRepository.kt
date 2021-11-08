package com.awesome.amumanager.data.store

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

interface StoreRepository {
    fun getStore(stores: MutableLiveData<ArrayList<Store>>, uid: String, lastId: String)
    fun addStore(store : Store, status : MutableLiveData<Int>)
    fun deleteStore(storeId : String, status : MutableLiveData<Int>)
    fun openStore(storeId : String, status : MutableLiveData<Int>)
    fun closeStore(storeId : String, status : MutableLiveData<Int>)
}

class AmuManagerStoreRepository @Inject constructor(
    private val amuManagerStoreDataSource: AmuManagerStoreDataSource
) : StoreRepository {
    override fun getStore(stores: MutableLiveData<ArrayList<Store>>, uid: String, lastId: String) {
        amuManagerStoreDataSource.getStore(stores, uid, lastId)
    }

    override fun addStore(store: Store, status: MutableLiveData<Int>) {
        amuManagerStoreDataSource.addStore(store, status)
    }

    override fun deleteStore(storeId: String, status: MutableLiveData<Int>) {
        amuManagerStoreDataSource.deleteStore(storeId, status)
    }

    override fun openStore(storeId: String, status: MutableLiveData<Int>) {
        amuManagerStoreDataSource.openStore(storeId, status)
    }

    override fun closeStore(storeId: String, status: MutableLiveData<Int>) {
        amuManagerStoreDataSource.closeStore(storeId, status)
    }
}

class AmuManagerStoreTestRepository @Inject constructor(
    private val amuManagerStoreTestDataSource: AmuManagerStoreTestDataSource
) : StoreRepository {
    override fun getStore(stores: MutableLiveData<ArrayList<Store>>, uid: String, lastId: String) {
        amuManagerStoreTestDataSource.getStore(stores, uid, lastId)
    }

    override fun addStore(store: Store, status: MutableLiveData<Int>) {
        TODO("Not yet implemented")
    }

    override fun deleteStore(storeId: String, status: MutableLiveData<Int>) {
        TODO("Not yet implemented")
    }

    override fun openStore(storeId: String, status: MutableLiveData<Int>) {
        TODO("Not yet implemented")
    }

    override fun closeStore(storeId: String, status: MutableLiveData<Int>) {
        TODO("Not yet implemented")
    }

}