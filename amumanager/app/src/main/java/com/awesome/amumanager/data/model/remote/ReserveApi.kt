package com.awesome.amumanager.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.ClientResponse
import com.awesome.amumanager.data.api.response.ReserveResponse
import com.awesome.amumanager.data.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveApi {

    fun getReserveList(
            mReserveLists: MutableLiveData<ArrayList<ReserveList>>,
            storeId: String
    ) {

        var reservesTemp = ArrayList<Reserve>()

        val joinApi = RetrofitObject.getReserveService

        joinApi.getReserveList(storeId)
                .enqueue(object : Callback<ReserveResponse> {

                    override fun onFailure(call: Call<ReserveResponse>, t: Throwable) {
                        Log.e("Main Retrofit getStore", "실패")
                    }

                    override fun onResponse(
                            call: Call<ReserveResponse>,
                            response: Response<ReserveResponse>
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

            val joinApi = RetrofitObject.getClientService
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