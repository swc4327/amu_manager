package com.awesome.amumanager.ui.main.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_review_detail.*

class ReviewDetailActivity : AppCompatActivity() {

    private var review : Review? = null
    private var client : Client? = null
    private var storeId : String? = ""

    private lateinit var reviewViewModel : ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        review = intent.getParcelableExtra("review")
        client = intent.getParcelableExtra("client")
        storeId = intent.getStringExtra("storeId")

        var factory = ReviewViewModelFactory(storeId.toString())
        reviewViewModel = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)

        reviewViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                Toast.makeText(this@ReviewDetailActivity, "1개의 리뷰를 삭제 했어요!!", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        })


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
            if(client!!.count!!.toInt() >= 3 && client!!.point!!.toDouble() <= 4) {
                reviewViewModel.reviewFiltering(review!!, client!!)
            }
            else {
                Toast.makeText(this, "리뷰를 삭제할 수 없습니다!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}