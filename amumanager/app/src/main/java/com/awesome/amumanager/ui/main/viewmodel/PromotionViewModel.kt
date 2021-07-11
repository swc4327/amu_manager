package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.remote.PromotionListApi

class PromotionViewModel(private var storeId: String) : ViewModel() {
    private val promotionListFactory = PromotionListApi()
    val promotionList = MutableLiveData<ArrayList<Promotion>>()
    val status = MutableLiveData<Int>()

    fun getPromotionList() {
        promotionListFactory.getPromotionList(promotionList, storeId)
    }

    fun addPromotion(promotion: Promotion) {
        promotionListFactory.addPromotion(promotion, status)
    }

}