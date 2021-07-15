package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.remote.PromotionApi

class PromotionViewModel(private var storeId: String) : ViewModel() {
    private val promotionListApi = PromotionApi()
    val promotionList = MutableLiveData<ArrayList<Promotion>>()
    val status = MutableLiveData<Int>()

    fun getPromotionList() {
        promotionListApi.getPromotionList(promotionList, storeId)
    }

    fun addPromotion(promotion: Promotion) {
        promotionListApi.addPromotion(promotion, status)
    }

}