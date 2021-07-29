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
    val status = MutableLiveData<Int>()

    fun getReviewList(itemCount : String) {
        reviewListApi.getReviewList(reviewLists, storeId!!, itemCount)
    }

    fun reviewFiltering(review : Review, client : Client) {
        reviewListApi.reviewFiltering(review, client, status)
    }
}