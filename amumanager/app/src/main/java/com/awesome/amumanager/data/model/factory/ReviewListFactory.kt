package com.awesome.amumanager.data.model.factory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.Constants
import com.awesome.amumanager.data.api.response.ClientResponse
import com.awesome.amumanager.data.api.response.ReviewListResponse
import com.awesome.amumanager.data.api.service.GetClientInfoService
import com.awesome.amumanager.data.api.service.GetReviewListService
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.data.model.ReviewList
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewListFactory {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    fun getReviewList(
            mReviewLists: MutableLiveData<ArrayList<ReviewList>>,
            storeId: String) {

        var reviewsTemp = ArrayList<Review>()

        val joinApi = retrofit.create(GetReviewListService::class.java)
        Log.e("store_id check", storeId.toString())


        joinApi.getReviewList(storeId.toString())
                .enqueue(object : Callback<ReviewListResponse> {

                    override fun onFailure(call: Call<ReviewListResponse>, t: Throwable) {
                        Log.e("Retrofit GetReview", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<ReviewListResponse>,
                            response: Response<ReviewListResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("Get ReviewList Retrofit" , "success")

                            reviewsTemp.addAll(response.body()!!.reviews)
                            getClientInfo(mReviewLists, reviewsTemp)

                        } else {

                        }
                    }
                })
    }
    private fun getClientInfo(mReviewLists: MutableLiveData<ArrayList<ReviewList>>,
                              reviewsTemp: ArrayList<Review>) {

        var clientsTemp : ArrayList<Client> = ArrayList<Client>()
        var reviewListsTemp = ArrayList<ReviewList>()

        val clientIds = reviewsTemp.map { it.client_id }.distinct()
        val disposable = Observable.just(clientIds)
                .concatMapIterable { it }
                .concatMap { clientId -> getClient(clientId) }
                .toList()
                .map { clients ->
                    clientsTemp.addAll(clients)
                    val reviewLists : ArrayList<ReviewList> = ArrayList<ReviewList>()
                    for (review in reviewsTemp) {
                        val client = clients.find { it.uid == review.client_id }
                        val reviewList = client?.let { ReviewList(it, review) }
                        reviewList?.let { reviewLists.add(it) }
                    }
                    reviewLists
                }
                .subscribe({ reviewLists ->
                    reviewListsTemp = reviewLists
                    mReviewLists.value = reviewListsTemp

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