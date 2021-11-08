package com.awesome.amumanager.domain.usecase.review

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.review.ReviewRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.model.ReviewList
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(private val repository: ReviewRepository) {
    operator fun invoke(ReviewLists: MutableLiveData<ArrayList<ReviewList>>,
                        storeId: String,
                        lastId: String) {
        repository.getReviewList(ReviewLists, storeId, lastId)
    }
}