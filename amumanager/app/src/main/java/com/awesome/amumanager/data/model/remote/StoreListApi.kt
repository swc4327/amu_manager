package com.awesome.amumanager.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.StoreListResponse
import com.awesome.amumanager.data.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreListApi {

    fun getStoreList(
            storeList: MutableLiveData<ArrayList<Store>>,
            uid: String
    ) {

        var storesTemp = ArrayList<Store>()

        val joinApi = RetrofitObject.getStoreListService

        joinApi.getStoreList(uid)
                .enqueue(object : Callback<StoreListResponse> {

                    override fun onFailure(call: Call<StoreListResponse>, t: Throwable) {
                        Log.e("Main Retrofit getStore", "실패")
                    }

                    override fun onResponse(
                            call: Call<StoreListResponse>,
                            response: Response<StoreListResponse>
                    ) {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            storesTemp.addAll(response.body()!!.stores)
                            storeList.value = storesTemp
                        } else {

                        }
                    }
                })
    }

    fun addStore(store : Store, status : MutableLiveData<Int>) {
        val joinApi = RetrofitObject.addStoreService

        joinApi.addStore(store)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("Retrofit Add Store", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("AddStoreActivity", "success")
                            status.value = 200

                        } else {
                            Log.e("AddStoreActivity", "실패")
                        }
                    }
                })
    }
}