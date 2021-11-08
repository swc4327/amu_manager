package com.awesome.amumanager.data.api.test

import com.awesome.amumanager.domain.model.Store

class StoresTestData {
    private val stores =  ArrayList<Store>()

    init {
        val store = Store(
            1,
            "zz",
            "https://firebasestorage.googleapis.com/v0/b/amumanager-30c5d.appspot.com/o/WhlOHUww4Rg1ezJSKPntU3rDcBu1%E3%85%8B%E3%85%8B?alt=media&token=134b7f61-a4c7-46d4-a7fd-7d3c3c060d1f",
            "WhlOHUww4Rg1ezJSKPntU3rDcBu1",
            "35.9535822",
            "128.5647181",
            "학정동 336",
            "zzz",
            "노래방",
            0,
            0.00f,
            "0"
        )
        stores.add(store)
    }

    fun getStoresTestData(): ArrayList<Store> {
        return stores
    }
}