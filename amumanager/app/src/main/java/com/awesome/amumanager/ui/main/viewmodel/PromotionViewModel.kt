package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.remote.PromotionApi

class PromotionViewModel(private var storeId: String) : ViewModel() {
    private val promotionApi = PromotionApi()
    val promotions = MutableLiveData<ArrayList<Promotion>>()
    val status = MutableLiveData<Int>()

    fun getPromotion(lastId : String) {
        promotionApi.getPromotion(promotions, storeId, lastId)
    }

    fun addPromotion(promotion: Promotion) {
        promotionApi.addPromotion(promotion, status)
    }

    fun endPromotion(promotionId : String) {
        promotionApi.endPromotion(promotionId, status)
    }

}