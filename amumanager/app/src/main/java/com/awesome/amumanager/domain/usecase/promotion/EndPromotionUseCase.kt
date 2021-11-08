package com.awesome.amumanager.domain.usecase.promotion

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.promotion.PromotionRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.model.Promotion
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class EndPromotionUseCase @Inject constructor(private val repository: PromotionRepository) {
    operator fun invoke(promotionId : String , status : MutableLiveData<Int>) {
        repository.endPromotion(promotionId, status)
    }
}