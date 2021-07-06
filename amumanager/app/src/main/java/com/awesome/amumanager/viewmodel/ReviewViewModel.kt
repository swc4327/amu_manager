package com.awesome.amumanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.model.ReviewList
import com.awesome.amumanager.model.retrofit.RetrofitFactory

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    private val reviews = ArrayList<ReviewList>()

    init {
        reviewLists.value = reviews
        //getReviewList()
    }

    fun getReviewList() {
        retrofitFactory.getReviewList(reviewLists, reviews, storeId!!)
    }
}