package com.awesome.amumanager.data.model.factory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.Constants
import com.awesome.amumanager.data.api.response.ClientResponse
import com.awesome.amumanager.data.api.response.ReserveListResponse
import com.awesome.amumanager.data.api.service.GetClientInfoService
import com.awesome.amumanager.data.api.service.GetReserveListService
import com.awesome.amumanager.data.model.*
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReserveListFactory {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()



    fun getReserveList(
            mReserveLists: MutableLiveData<ArrayList<ReserveList>>,
            storeId: String
    ) {

        var reservesTemp = ArrayList<Reserve>()

        val joinApi = retrofit.create(GetReserveListService::class.java)

        joinApi.getReserveList(storeId)
                .enqueue(object : Callback<ReserveListResponse> {

                    override fun onFailure(call: Call<ReserveListResponse>, t: Throwable) {
                        Log.e("Main Retrofit getStore", "실패")
                    }

                    override fun onResponse(
                            call: Call<ReserveListResponse>,
                            response: Response<ReserveListResponse>
                    )  {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            reservesTemp.addAll(response.body()!!.reserves)
                            getClientInfo(mReserveLists, reservesTemp)
                        } else {

                        }
                    }
                })
    }

    private fun getClientInfo(mReserveLists: MutableLiveData<ArrayList<ReserveList>>,
                              reservesTemp: ArrayList<Reserve>) {

        var clientsTemp : ArrayList<Client> = ArrayList<Client>()
        var reserveListsTemp = ArrayList<ReserveList>()

        val clientIds = reservesTemp.map { it.client_id }.distinct()
        val disposable = Observable.just(clientIds)
                .concatMapIterable { it }
                .concatMap { clientId -> getClient(clientId) }
                .toList()
                .map { clients ->
                    clientsTemp.addAll(clients)
                    val reserveLists : ArrayList<ReserveList> = ArrayList<ReserveList>()
                    for (reserve in reservesTemp) {
                        val client = clients.find { it.uid == reserve.client_id }
                        val reserveList = client?.let { ReserveList(it, reserve) }
                        reserveList?.let { reserveLists.add(it) }
                    }
                    reserveLists
                }
                .subscribe({ reserveLists ->
                    reserveListsTemp = reserveLists
                    mReserveLists.value = reserveListsTemp

                }, {

                })
    }

    private fun getClient(clientId: String): Observable<Client> {
        return Observable.create { emitter ->

            val joinApi = retrofit.create(GetClientInfoService::class.java)
            joinApi.getClient(clientId)
                    .enqueue(object : Callback<ClientResponse> {

                        override fun onFailure(
                                call: Call<ClientResponse>,
                                t: Throwable
                        ) {
                            emitter.onError(t)
                        }

                        override fun onResponse(
                                call: Call<ClientResponse>,
                                response: Response<ClientResponse>
                        ) {
                            if (response.body() != null) {
                                emitter.onNext(response.body()!!.client)
                                emitter.onComplete()
                            }
                        }
                    })
        }
    }
}