package com.awesome.amumanager.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.ClientResponse
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.ReviewResponse
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Constants.FIRST_CALL
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.data.model.ReviewList
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewApi {

    fun getReviewList(
            ReviewLists: MutableLiveData<ArrayList<ReviewList>>,
            storeId: String,
            lastId: String,
            reviewsTemp: ArrayList<Review>)
    {

        //var reviewsTemp = ArrayList<Review>()

        val joinApi = RetrofitObject.reviewService

        joinApi.getReviewList(storeId.toString(), lastId)
                .enqueue(object : Callback<ReviewResponse> {

                    override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                        Log.e("Retrofit GetReview", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<ReviewResponse>,
                            response: Response<ReviewResponse>
                    )  {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("Get ReviewList Retrofit" , "success")

                            if(lastId == FIRST_CALL && reviewsTemp.isNotEmpty()) {
                                reviewsTemp.clear()
                            }

                            reviewsTemp.addAll(response.body()!!.reviews)
                            getClientInfo(ReviewLists, reviewsTemp)

                        } else {

                        }
                    }
                })
    }
    private fun getClientInfo(ReviewLists: MutableLiveData<ArrayList<ReviewList>>,
                              reviewsTemp: ArrayList<Review>) {

        var clientsTemp : ArrayList<Client> = ArrayList<Client>()

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
                    ReviewLists.value = reviewLists

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

    fun reviewFiltering(review: Review, client: Client, status : MutableLiveData<Int>) {
        val joinApi = RetrofitObject.reviewService


        joinApi.filterReview(review!!.id.toString(), review!!.store_id)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("review filtering", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("review filtering", "success")
                        status.value = 200
                    } else {

                    }
                }
            })
    }
}