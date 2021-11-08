package com.awesome.amumanager.domain.usecase.review

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.review.ReviewRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.*
import javax.inject.Inject

class ReviewFilteringUseCase @Inject constructor(private val repository: ReviewRepository) {
    operator fun invoke(review: Review, client: Client, status: MutableLiveData<Int>) {
        repository.reviewFiltering(review, client, status)
    }
}