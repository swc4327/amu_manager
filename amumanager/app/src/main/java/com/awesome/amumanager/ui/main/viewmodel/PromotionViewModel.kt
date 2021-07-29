package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.remote.PromotionApi

class PromotionViewModel(private var storeId: String) : ViewModel() {
    private val promotionApi = PromotionApi()
    val promotions = MutableLiveData<ArrayList<Promotion>>()
    private var promotionsTemp = ArrayList<Promotion>()
    val status = MutableLiveData<Int>()

    fun getPromotion(itemCount : String) {
        promotionApi.getPromotion(promotions, storeId, itemCount, promotionsTemp)
    }

    fun addPromotion(promotion: Promotion) {
        promotionApi.addPromotion(promotion, status)
    }

}