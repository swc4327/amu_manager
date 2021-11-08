package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.Promotion
import com.awesome.amumanager.domain.usecase.promotion.AddPromotionUseCase
import com.awesome.amumanager.domain.usecase.promotion.EndPromotionUseCase
import com.awesome.amumanager.domain.usecase.promotion.GetPromotionUseCase
import javax.inject.Inject

class PromotionViewModel @Inject constructor(
    private val getPromotionUseCase: GetPromotionUseCase,
    private val addPromotionUseCase: AddPromotionUseCase,
    private val endPromotionUseCase: EndPromotionUseCase
) : ViewModel() {
    val promotions = MutableLiveData<ArrayList<Promotion>>()
    val status = MutableLiveData<Int>()

    fun getPromotion(lastId : String, storeId : String) {
        getPromotionUseCase(promotions, storeId, lastId)
    }

    fun addPromotion(promotion: Promotion) {
        addPromotionUseCase(promotion, status)
    }

    fun endPromotion(promotionId : String) {
        endPromotionUseCase(promotionId, status)
    }

}