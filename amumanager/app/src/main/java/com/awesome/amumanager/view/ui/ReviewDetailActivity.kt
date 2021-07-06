package com.awesome.amumanager.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.api.service.ReviewFilteringService
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Review
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_review_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewDetailActivity : AppCompatActivity() {

    private var review : Review? = null
    private var client : Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        review = intent.getParcelableExtra("review")
        client = intent.getParcelableExtra("client")


        review_detail_client_name.setText(client!!.nickname)
        review_detail_client_count.setText("리뷰 수 " + client!!.count.toString())
        review_detail_client_point.setText("평균 평점 " + client!!.point.toString())
        Glide
            .with(this)
            .load(client!!.image)
            .into(profile_img)

        close_review_detail.setOnClickListener {
            finish()
        }

        review_filtering.setOnClickListener {
            if(client!!.count!!.toInt() >= 3 && client!!.point!!.toDouble() <= 1.50) {
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
                        Toast.makeText(this@ReviewDetailActivity, "1개의 리뷰를 삭제 했어요!!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {

                    }
                }
            })
    }
}