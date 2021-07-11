package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.data.model.remote.ReviewListApi

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val reviewListFactory = ReviewListApi()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    val status = MutableLiveData<Int>()

    fun getReviewList() {
        reviewListFactory.getReviewList(reviewLists, storeId!!)
    }

    fun reviewFiltering(review : Review, client : Client) {
        reviewListFactory.reviewFiltering(review, client, status)
    }
}