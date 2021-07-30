package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.data.model.remote.ReviewApi

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val reviewListApi = ReviewApi()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    private var reviewsTemp = ArrayList<Review>()
    val status = MutableLiveData<Int>()

    fun getReviewList(lastId : String) {
        reviewListApi.getReviewList(reviewLists, storeId!!, lastId, reviewsTemp)
    }

    fun reviewFiltering(review : Review, client : Client) {
        reviewListApi.reviewFiltering(review, client, status)
    }
}