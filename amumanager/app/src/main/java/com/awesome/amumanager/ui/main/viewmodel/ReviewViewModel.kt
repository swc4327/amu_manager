package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.data.model.factory.ReviewListFactory

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val reviewListFactory = ReviewListFactory()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()

    fun getReviewList() {
        reviewListFactory.getReviewList(reviewLists, storeId!!)
    }
}