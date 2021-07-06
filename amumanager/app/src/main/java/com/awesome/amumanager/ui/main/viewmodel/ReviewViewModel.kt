package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.data.model.retrofit.RetrofitFactory

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    //private val reviews = ArrayList<ReviewList>()

    fun getReviewList() {
        retrofitFactory.getReviewList(reviewLists, storeId!!)
    }
}