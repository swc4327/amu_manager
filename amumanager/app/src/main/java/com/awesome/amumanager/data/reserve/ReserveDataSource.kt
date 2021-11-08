package com.awesome.amumanager.data.reserve

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.ClientResponse
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.ReserveResponse
import com.awesome.amumanager.data.api.service.ClientApi
import com.awesome.amumanager.data.api.service.ReserveApi
import com.awesome.amumanager.domain.model.Client
import com.awesome.amumanager.domain.model.Reserve
import com.awesome.amumanager.domain.model.ReserveList
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface ReserveDataSource {
    fun getReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    )
    fun getConfirmedReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    )
    fun confirmReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    )
    fun cancelReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    )
    fun completeReserve(
        reserveId : String,
        status : MutableLiveData<Int>
    )
}

class AmuManagerReserveDataSource @Inject constructor(
    private val reserveApi: ReserveApi,
    private val clientApi: ClientApi
) : ReserveDataSource {
    override fun getReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    ) {

        reserveApi.getReserveList(storeId, lastId)
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
                        getClientInfo(ReserveLists, response.body()!!.reserves)
                    } else {

                    }
                }
            })
    }

    override fun getConfirmedReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        storeId: String,
        lastId: String
    ) {


        reserveApi.getConfirmedReserveList(storeId, lastId)
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
                        getClientInfo(ReserveLists, response.body()!!.reserves)
                    } else {

                    }
                }
            })
    }

    private fun getClientInfo(ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
                              reservesTemp: ArrayList<Reserve>) {

        val clientIds = reservesTemp.map { it.clientId }.distinct()
        val disposable = Observable.just(clientIds)
            .flatMapIterable { it }
            .flatMap { clientId -> getClient(clientId) }
            .toList()
            .map { clients ->
                val reserveLists : ArrayList<ReserveList> = ArrayList<ReserveList>()
                for (reserve in reservesTemp) {
                    val client = clients.find { it.uid == reserve.clientId }
                    val reserveList = client?.let { ReserveList(it, reserve) }
                    reserveList?.let { reserveLists.add(it) }
                }
                reserveLists
            }
            .subscribe({ reserveLists ->
                ReserveLists.value = reserveLists

            }, {

            })
    }

    private fun getClient(clientId: String): Observable<Client> {
        return Observable.create { emitter ->
            clientApi.getClient(clientId)
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

    override fun confirmReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    ) {

        reserveApi.confirmReserve(reserveId)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("confirm Reserve", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Confirm Reserve", "success")
                        status.value = 200
                    } else {
                        Log.e("Confirm Reserve", "실패")
                    }
                }
            })
    }

    override fun cancelReserve(
        reserveId: String,
        status: MutableLiveData<Int>
    ) {

        reserveApi.cancelReserve(reserveId)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("cancel Reserve", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("cancel Reserve", "success")
                        status.value = 200
                    } else {
                        Log.e("cancel Reserve", "실패")
                    }
                }
            })
    }
    override fun completeReserve(
        reserveId : String,
        status : MutableLiveData<Int>
    ) {

        reserveApi.completeReserve(reserveId)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Complete Reserve", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Complete Reserve", "success")
                        status.value = 200
                    } else {
                        Log.e("Complete Reserve", "실패")
                    }
                }
            })
    }
}