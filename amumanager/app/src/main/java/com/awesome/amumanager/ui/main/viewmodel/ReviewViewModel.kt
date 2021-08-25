package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Review
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.data.model.remote.ReviewApi
import javax.inject.Inject

class ReviewViewModel @Inject constructor() : ViewModel() {
    private val reviewListApi = ReviewApi()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    val status = MutableLiveData<Int>()

    fun getReviewList(lastId : String, storeId : String) {
        reviewListApi.getReviewList(reviewLists, storeId, lastId)
    }

    fun reviewFiltering(review : Review, client : Client) {
        reviewListApi.reviewFiltering(review, client, status)
    }
}