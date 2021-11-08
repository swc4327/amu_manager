package com.awesome.amumanager.presentation.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.domain.model.Client
import com.awesome.amumanager.domain.model.Review
import com.awesome.amumanager.domain.model.ReviewList
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_review_detail.*
import javax.inject.Inject

class ReviewDetailActivity : BaseActivity() {

    private var review : Review? = null
    private var client : Client? = null
    private var storeId : String? = ""

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var reviewViewModel : ReviewViewModel

    companion object {
        fun startActivity(activity: BaseActivity, reviewList : ReviewList, storeId : String) {
            val intent = Intent(activity, ReviewDetailActivity::class.java)
            intent.putExtra("review", reviewList.review)
            intent.putExtra("client", reviewList.client)
            intent.putExtra("storeId", storeId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        review = intent.getParcelableExtra("review")
        client = intent.getParcelableExtra("client")
        storeId = intent.getStringExtra("storeId")

        reviewViewModel = ViewModelProvider(this, viewModelFactory).get(ReviewViewModel::class.java)

        initLayout()
        initListener()
        observe()
    }

    private fun initListener() {
        close_review_detail.setOnClickListener {
            finish()
        }

        review_filtering.setOnClickListener {
            if(client?.count?.toInt()!! >= 2 && client?.point?.toDouble()!! <= 4) {
                reviewViewModel.reviewFiltering(review!!, client!!)
            }
            else {
                Toast.makeText(this, "리뷰를 삭제할 수 없습니다!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initLayout() {
        review_detail_client_name.text = client?.nickname
        review_detail_client_count.text = "리뷰 수 " + client?.count.toString()
        review_detail_client_point.text = "평균 평점 " + client?.point.toString()
        Glide
            .with(this)
            .load(client!!.image)
            .into(profile_img)
    }

    private fun observe() {
        reviewViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                Toast.makeText(this@ReviewDetailActivity, "1개의 리뷰를 삭제 했어요!!", Toast.LENGTH_LONG).show()
                finish()
            }
        })

    }
}