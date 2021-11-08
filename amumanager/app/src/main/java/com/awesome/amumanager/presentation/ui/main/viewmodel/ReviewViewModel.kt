package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.Client
import com.awesome.amumanager.domain.model.Review
import com.awesome.amumanager.domain.model.ReviewList
import com.awesome.amumanager.domain.usecase.review.GetReviewUseCase
import com.awesome.amumanager.domain.usecase.review.ReviewFilteringUseCase
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
    private val reviewFilteringUseCase: ReviewFilteringUseCase
) : ViewModel() {

    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    val status = MutableLiveData<Int>()

    fun getReviewList(lastId : String, storeId : String) {
        getReviewUseCase(reviewLists, storeId, lastId)
    }

    fun reviewFiltering(review : Review, client : Client) {
        reviewFilteringUseCase(review, client, status)
    }
}