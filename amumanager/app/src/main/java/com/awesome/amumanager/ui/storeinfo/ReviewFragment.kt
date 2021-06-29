package com.awesome.amumanager.ui.storeinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.ClientResponse
import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.api.response.ReviewListResponse
import com.awesome.amumanager.api.service.GetClientInfoService
import com.awesome.amumanager.api.service.GetReviewListService
import com.awesome.amumanager.api.service.ReviewFilteringService
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Review
import com.awesome.amumanager.model.ReviewList
import com.awesome.amumanager.ui.ReserveDetailActivity
import com.awesome.amumanager.ui.ReviewDetailActivity
import com.awesome.amumanager.ui.adapter.ReviewListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_reserve_list.view.*
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.fragment_review.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class ReviewFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var reviews : ArrayList<Review> = ArrayList<Review>()
    private var client = Client("","","","","")
    private var reviewListAdapter: ReviewListAdapter? = null
    private var storeId: String? = ""
    private var clientList : ArrayList<Client> = ArrayList<Client>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.review_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), ReviewDetailActivity::class.java)
            intent.putExtra("clientId", reviewListAdapter!!.getItemClientId(position).toString())
            intent.putExtra("clientName", reviewListAdapter!!.getItemClientName(position).toString())
            intent.putExtra("clientImage", reviewListAdapter!!.getItemClientImage(position).toString())
            intent.putExtra("clientCount", reviewListAdapter!!.getItemClientCount(position).toString())
            intent.putExtra("clientPoint", reviewListAdapter!!.getItemClientPoint(position).toString())
            intent.putExtra("storeId", storeId)
            intent.putExtra("reviewId", reviewListAdapter!!.getItemReviewId(position).toString())
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_review, container, false)

        auth = FirebaseAuth.getInstance()
        storeId = arguments?.getString("store_id")
        getReviewList()

//        view.review_filtering.setOnClickListener {
//            //리뷰 필터링
//            println("*****"+clientList.size)
//            for (i in 0 until clientList.size) {
//                if(clientList[i].count!!.toInt() >= 3 && clientList[i].point!!.toFloat() <= 3) {
//                    for (j in 0 until reviews.size) {
//                        if(reviews[j].client_id == clientList[i].uid) {
//                            reviewFiltering(reviews[j].id)
//
//                        }
//                    }
//                }
//            }
//        }
        return view
    }


    private fun getReviewList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

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

                        reviews.addAll(response.body()!!.reviews)
                        getClientInfo()

                    } else {

                    }
                }
            })
    }
    private fun getClientInfo() {
        val clientIds = this.reviews.map { it.client_id }.distinct()
        val disposable = Observable.just(clientIds)
                .concatMapIterable { it }
                .concatMap { clientId -> getClient(clientId) }
                .toList()
                .map { clients ->
                    clientList.addAll(clients)
                    val reviewLists : ArrayList<ReviewList> = ArrayList<ReviewList>()
                    for (review in reviews) {
                        val client = clients.find { it.uid == review.client_id }
                        val reviewList = client?.let { ReviewList(it, review) }
                        reviewList?.let { reviewLists.add(it) }
                    }
                    reviewLists
                }
                .subscribe({ reviewLists ->
                    reviewListAdapter = ReviewListAdapter(requireContext(), reviewLists)
                    review_list.adapter = reviewListAdapter

                }, {

                })
    }

    private fun getClient(clientId: String): Observable<Client> {
        return Observable.create { emitter ->
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.serverUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

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