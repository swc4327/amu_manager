package com.awesome.amumanager.data.review

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.Client
import com.awesome.amumanager.domain.model.Review
import com.awesome.amumanager.domain.model.ReviewList
import com.awesome.amumanager.domain.model.Store
import io.reactivex.Observable
import javax.inject.Inject

interface ReviewRepository {
    fun getReviewList(
        ReviewLists: MutableLiveData<ArrayList<ReviewList>>,
        storeId: String,
        lastId: String
    )

    fun reviewFiltering(review: Review, client: Client, status: MutableLiveData<Int>)
}

class AmuManagerReviewRepository @Inject constructor(
    private val reviewDataSource: ReviewDataSource
) : ReviewRepository {
    override fun getReviewList(
        ReviewLists: MutableLiveData<ArrayList<ReviewList>>,
        storeId: String,
        lastId: String
    ) {
        reviewDataSource.getReviewList(ReviewLists, storeId, lastId)
    }

    override fun reviewFiltering(review: Review, client: Client, status: MutableLiveData<Int>) {
        reviewDataSource.reviewFiltering(review, client, status)
    }

}