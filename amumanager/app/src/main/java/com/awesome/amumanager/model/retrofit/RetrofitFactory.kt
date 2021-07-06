package com.awesome.amumanager.model.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.ClientResponse
import com.awesome.amumanager.api.response.MenuListResponse
import com.awesome.amumanager.api.response.ReviewListResponse
import com.awesome.amumanager.api.response.StoreListResponse
import com.awesome.amumanager.api.service.GetClientInfoService
import com.awesome.amumanager.api.service.GetMenuListService
import com.awesome.amumanager.api.service.GetReviewListService
import com.awesome.amumanager.api.service.GetStoreListService
import com.awesome.amumanager.model.*
import com.awesome.amumanager.util.FirebaseUtils
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.serverUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()



    fun getStoreList(
            storeList: MutableLiveData<ArrayList<Store>>,
            stores: ArrayList<Store>
    ) {
        val joinApi = retrofit.create(GetStoreListService::class.java)
        val uid = FirebaseUtils.getUid()

        joinApi.getStoreList(uid)
            .enqueue(object : Callback<StoreListResponse> {

                override fun onFailure(call: Call<StoreListResponse>, t: Throwable) {
                    Log.e("Main Retrofit getStore", "실패")
                }

                override fun onResponse(
                    call: Call<StoreListResponse>,
                    response: Response<StoreListResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        if(stores.isNotEmpty()) {
                            stores.clear()
                        }
                        stores.addAll(response.body()!!.stores)
                        storeList.value = stores
                    } else {

                    }
                }
            })
    }


    fun getMenuList(
            menuList: MutableLiveData<ArrayList<Menu>>,
            menus: ArrayList<Menu>,
            storeId: String) {
        val joinApi = retrofit.create(GetMenuListService::class.java)

        joinApi.getMenuList(storeId.toString())
                .enqueue(object : Callback<MenuListResponse> {

                    override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
                        Log.e("Menu Retrofit getMenu", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<MenuListResponse>,
                            response: Response<MenuListResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("Getmenu Retrofit", "success")
                            menus.addAll(response.body()!!.menus)
                            menuList.value = menus
                        } else {

                        }
                    }
                })
    }








    fun getReviewList(
            mReviewLists: MutableLiveData<ArrayList<ReviewList>>,
            reviews: ArrayList<ReviewList>,
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
                            getClientInfo(mReviewLists, reviews, reviewsTemp)

                        } else {

                        }
                    }
                })
    }
    private fun getClientInfo(mReviewLists: MutableLiveData<ArrayList<ReviewList>>,
                              reviews: ArrayList<ReviewList>,
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
                    if(reviews.isNotEmpty()) {
                        reviews.clear()
                    }
                    reviews.addAll(reviewLists)
                    mReviewLists.value = reviews
//                    reviewListAdapter = ReviewListAdapter(requireContext(), reviewLists)
//                    review_list.adapter = reviewListAdapter

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