package com.awesome.amumanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.api.service.ReviewFilteringService
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_review_detail.*
import kotlinx.android.synthetic.main.reviewlist_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewDetailActivity : AppCompatActivity() {

    private var clientId = ""
    private var clientName = ""
    private var clientImage = ""
    private var clientCount = ""
    private var clientPoint = ""
    private var storeId = ""
    private var reviewId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        clientId = intent.getStringExtra("clientId").toString()
        clientName = intent.getStringExtra("clientName").toString()
        clientImage = intent.getStringExtra("clientImage").toString()
        clientCount = intent.getStringExtra("clientCount").toString()
        clientPoint = intent.getStringExtra("clientPoint").toString()

        storeId = intent.getStringExtra("storeId").toString()
        reviewId = intent.getStringExtra("reviewId").toString()

        review_detail_client_name.setText(clientName)
        review_detail_client_count.setText("리뷰 수 " + clientCount)
        review_detail_client_point.setText("평균 평점 " + clientPoint)
        Glide
            .with(this)
            .load(clientImage)
            .into(profile_img)

        close_review_detail.setOnClickListener {
            finish()
        }

        review_filtering.setOnClickListener {
            if(clientCount.toInt() >= 3 && clientPoint.toDouble() <= 1.50) {
                reviewFiltering()
            }
            else {
                Toast.makeText(this, "리뷰를 삭제할 수 없습니다!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun reviewFiltering() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(ReviewFilteringService::class.java)


        joinApi.filterReview(reviewId, storeId)
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
                        Toast.makeText(this@ReviewDetailActivity, "1개의 리뷰를 삭제 했어요!!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {

                    }
                }
            })
    }
}